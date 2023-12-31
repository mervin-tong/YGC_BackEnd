package com.piesat.school.biz.ds.uploadpermissions.service;

import com.piesat.school.biz.ds.uploadpermissions.entity.UploadPermissions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.piesat.school.uploadpermissions.param.UploadPermissionAddParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionCloseUpParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionOperateParamData;
import com.piesat.school.uploadpermissions.param.UploadPermissionsParamData;
import com.piesat.school.uploadpermissions.vto.UploadPermissionsVTO;
import com.piesat.school.uploadpermissions.vto.UserPermissionVTO;
import com.smartwork.api.support.page.TailPage;

/**
 * <p>
 *  上传权限类
 * </p>
 *
 * @author suweipeng
 * @since 2022-02-28
 */
public interface IUploadPermisssionsService extends IService<UploadPermissions> {

    //获取权限申请列表
    TailPage<UploadPermissionsVTO> uploadPermissionsList(UploadPermissionsParamData uploadPermissionsParamData);
    //创建权限申请
    Boolean createPermissions(UploadPermissionAddParamData paramData);
    //审批人处理申请
    Boolean checkPermissions(UploadPermissionOperateParamData paramData);
    //审批人锁定申请
    Boolean setApprover(Long approver, String uploadIds,Long limit);
    //审批人解锁
    Boolean cleanApprover(Long approver, String uploadIds);

    UserPermissionVTO userPermissions(Long userId);

    Boolean closeUpPermissions(UploadPermissionCloseUpParamData paramData);

    UploadPermissionsVTO detail(Long id);
}
