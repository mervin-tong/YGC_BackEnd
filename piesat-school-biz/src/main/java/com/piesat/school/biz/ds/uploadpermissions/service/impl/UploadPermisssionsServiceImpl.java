package com.piesat.school.biz.ds.uploadpermissions.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.piesat.school.biz.ds.uploadpermissions.entity.UploadPermissions;
import com.piesat.school.biz.ds.uploadpermissions.mapper.UploadPermissionsMapper;
import com.piesat.school.biz.ds.uploadpermissions.scheduled.ScheduleTask;
import com.piesat.school.biz.ds.uploadpermissions.service.IUploadPermisssionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.piesat.school.biz.ds.user.entity.User;
import com.piesat.school.biz.ds.user.service.IUserService;
import com.piesat.school.emuerlation.BizEnumType;
import com.piesat.school.uploadpermissions.param.UploadPermissionAddParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionCloseUpParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionOperateParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionsParamData;
import com.piesat.school.uploadpermissions.vto.UploadPermissionsVTO;
import com.piesat.school.uploadpermissions.vto.UserPermissionVTO;
import com.smartwork.api.support.page.CommonPage;
import com.smartwork.api.support.page.TailPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  上传权限类
 * </p>
 *
 * @author suweipeng
 * @since 2022-02-28
 */
@Slf4j
@Service
public class UploadPermisssionsServiceImpl extends ServiceImpl<UploadPermissionsMapper, UploadPermissions> implements IUploadPermisssionsService {

    @Resource
    private UploadPermissionsMapper uploadPermissionsMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Resource
    private IUserService userService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private ScheduleTask scheduleTask;
    /**
     * 获取申请上传权限列表
     * @param uploadPermissionsParamData
     * @return
     */
    @Override
    public TailPage<UploadPermissionsVTO> uploadPermissionsList(UploadPermissionsParamData uploadPermissionsParamData) {
        Page<UploadPermissionsVTO> page = new Page<>(uploadPermissionsParamData.getPn(),uploadPermissionsParamData.getPs());
        List<UploadPermissionsVTO> list = uploadPermissionsMapper.uploadPermissionsList(uploadPermissionsParamData.getUploadPermissionsStatus(),uploadPermissionsParamData.getStartAt(),uploadPermissionsParamData.getEndAt(),uploadPermissionsParamData.getCondition(),page);
        list.stream().filter(o->o.getApprover()!=-1L&&o.getApprover()!=0&&o.getApprover()!=null).forEach(o->o.setCheckMan(userService.getById(o.getApprover()).getName()));
        return CommonPage.buildPage(page.getCurrent(),page.getSize(),page.getTotal(),list);
    }

    /**
     * 申请上传权限
     * @return true 申请成功
     */
    @Override
    public Boolean createPermissions(UploadPermissionAddParamData paramData) {
        UploadPermissions uploadPermissions = new UploadPermissions();
        uploadPermissions.setStatus(BizEnumType.UploadPermissionsStatus.CreatePermissions.getKey());
        uploadPermissions.setApplicatId(paramData.getUserId());
        uploadPermissions.setReason(paramData.getReason());
        uploadPermissions.setCreatedAt(new Date());
//        uploadPermissions.setApprover(0L);//0代表该申请没有管理员审核
        if (uploadPermissionsMapper.insert(uploadPermissions) >= 1){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 审批人锁定申请
     * @param approver 审批人
     * @param uploadIds 上传权限ids
     * @param limit 锁定的时间（秒）
     * @return true 申请成功
     */

    @Override
    public Boolean setApprover(Long approver, String uploadIds,Long limit) {
        if(approver == null || uploadIds == null || limit == null){
            return Boolean.FALSE;
        }
        List<String> uploadIdList= Arrays.asList(uploadIds.split(","));
        //在redis创建map类型
        for(String i:uploadIdList){
            RSetMultimap<String, String> map = redissonClient.getSetMultimap("timestampUploadId");
            UpdateWrapper<UploadPermissions> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("approver",approver);
            updateWrapper.eq("id",Long.valueOf(i));
            updateWrapper.eq("approver",-1); //如果该申请已经被锁定则锁定失败
            long timestamp = System.currentTimeMillis()/1000;//以秒为单位的时间戳
            String limitTimestamp = String.valueOf(timestamp+limit);//到期的时间戳
            boolean isUpdate = this.update(updateWrapper);
            if (isUpdate){ //是否锁定成功
                log.info("到期时间 "+limitTimestamp+" "+isUpdate);
                map.put(limitTimestamp,i);//把到期时间和 订单编号放入缓存
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean cleanApprover(Long approver, String uploadIds) {
        if(approver == null || uploadIds == null){
            return Boolean.FALSE;
        }
        List<String> uploadIdList= Arrays.asList(uploadIds.split(","));
        //在redis创建map类型
        for(String i:uploadIdList){
            UpdateWrapper<UploadPermissions> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("approver",-1);
            updateWrapper.eq("id",Long.valueOf(i));
            updateWrapper.eq("approver",approver); //如果该申请已经被锁定则锁定失败
            boolean isUpdate = this.update(updateWrapper);
        }
        return Boolean.TRUE;
    }

    @Override
    public UserPermissionVTO userPermissions(Long userId) {
        UserPermissionVTO vto=new UserPermissionVTO();
        User user=this.userService.getById(userId);
        vto.setId(userId);
        vto.setIsDataUpload(user.getIsDataUpload());
        vto.setStatus(user.getStatus());
        vto.setCurrentUpload(user.getCurrentUpload());
        QueryWrapper<UploadPermissions> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(UploadPermissions::getApplicatId,userId);
        queryWrapper.lambda().orderByDesc(UploadPermissions::getUpdatedAt);
        List<UploadPermissions> uploadPermissions= this.list(queryWrapper);
        if (uploadPermissions!=null&&uploadPermissions.size()>0) {
            if (uploadPermissions.get(0).getStatus()==0) {
            vto.setIsApply(true);
            }

            vto.setRejectMark(uploadPermissions.get(0).getAuditMark());
        }
        return vto;
    }

    @Override
    public Boolean closeUpPermissions(UploadPermissionCloseUpParamData paramData) {
        User user=this.userService.getById(paramData.getUserId());
        user.setCurrentUpload(paramData.getIsClose()?false:true);
        this.userService.updateById(user);
        return Boolean.TRUE;
    }

    @Override
    public UploadPermissionsVTO detail(Long id) {
        UploadPermissionsVTO vto=new UploadPermissionsVTO();
        UploadPermissions uploadPermissions=this.getById(id);
        BeanUtils.copyProperties(uploadPermissions,vto);
        vto.setUserId(uploadPermissions.getApplicatId());
        User user=this.userService.getById(uploadPermissions.getApplicatId());
        vto.setEmail(user.getEmail());
        vto.setName(user.getName());
        vto.setProfession(user.getProfession());
        vto.setUnitAddress(user.getUnitAddress());
        vto.setApplyStatus(uploadPermissions.getStatus());
        if (uploadPermissions.getStatus()!=0){
            vto.setUpdatedAt(uploadPermissions.getUpdatedAt());
        }
        return vto;
    }

    /**
     * 审批人处理申请
     * @return true 申请成功
     */
    @Override
    public Boolean checkPermissions(UploadPermissionOperateParamData paramData) {
        if (paramData.getApprover() == null || paramData.getId() == null || paramData.getIsAllow() == null){
            return Boolean.FALSE;
        }
        //查询评审人是否锁定了该条申请
        UploadPermissions uploadPermissions=this.getById(paramData.getId());
        //判断是否为锁定的管理员在处理

        if(uploadPermissions.getApprover()!=-1&&uploadPermissions.getApprover() != paramData.getApprover()){
            return Boolean.FALSE;
        }
        uploadPermissions.setApprover(paramData.getApprover());
        //评审人处理该申请
        if(paramData.getIsAllow()){
            uploadPermissions.setStatus(BizEnumType.UploadPermissionsStatus.Pass.getKey());
        }else {
            uploadPermissions.setStatus(BizEnumType.UploadPermissionsStatus.Reject.getKey());
            if(StringUtils.isNotBlank(paramData.getMark())){
                uploadPermissions.setAuditMark(paramData.getMark());
            }
        }
        this.updateById(uploadPermissions);
//        isPermissionsLockDate(paramData.getId());
        if(paramData.getIsAllow()){
            User user=this.userService.getById(uploadPermissions.getApplicatId());
            user.setIsDataUpload(true);
            user.setCurrentUpload(true);
            this.userService.updateById(user);
        }
        return Boolean.TRUE;
    }
    public Boolean isPermissionsLockDate(Long uploadId) {
        QueryWrapper<UploadPermissions> queryWrapper = new QueryWrapper<>();
        UploadPermissions uploadPermissions = uploadPermissionsMapper.selectList(queryWrapper).get(0);
        if (uploadPermissions.getStatus() == BizEnumType.UploadPermissionsStatus.CreatePermissions.getKey()) {
            UpdateWrapper<UploadPermissions> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("approver", -1);
            updateWrapper.eq("id", uploadId);
            return this.update(updateWrapper);
        }
        return Boolean.TRUE;
    }
}
