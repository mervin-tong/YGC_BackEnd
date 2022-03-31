package com.piesat.school.biz.ds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.piesat.school.biz.ds.user.bulider.UserBuilder;
import com.piesat.school.biz.ds.user.entity.User;
import com.piesat.school.biz.ds.user.mapper.UserMapper;
import com.piesat.school.biz.ds.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.piesat.school.user.param.UserParamData;
import com.piesat.school.user.vto.UserListVTO;
import com.piesat.school.user.vto.UserVTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 唐子超
 * @since 2022-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Resource
    private UserMapper userMapper;
    @Override
    public UserVTO findUserByPhoneOrEmail(String phoneOrEmail) {
//        User user = new User();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //判断是不是手机号
//        if (CheckPhoneOrEmail.checkPhone(phoneOrEmail)){
//            queryWrapper.eq("phone",phoneOrEmail);
//            user = userMapper.selectList(queryWrapper).get(0);
//            return UserBulider.toUserVTO(user);
//        }
        //如果不是手机号用邮箱查询
        queryWrapper.eq("email",phoneOrEmail);
        User user = userMapper.selectList(queryWrapper).get(0);
        return UserBuilder.toUserVTO(user);
    }

    @Override
    public UserVTO addUser(UserParamData userParamData)  {
        User user = UserBuilder.toUser(userParamData);
        user.setPassword(passwordEncoder.encode(userParamData.getPassword()));
        this.save(user);
        return UserBuilder.toUserVTO(user);
    }

    @Override
    public Boolean alterPassword(String email, String password) {
        if (email == null && password == null){
            return Boolean.FALSE;
        }

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password",passwordEncoder.encode(password));
        updateWrapper.eq("email",email);
        return this.update(updateWrapper);
    }

    //获取用户列表
    @Override
    public List<UserListVTO> getUserList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(queryWrapper);
        List<UserListVTO> listVTOS = new ArrayList<>();
        for (User user : users) {
            listVTOS.add(UserBuilder.toUserListVTO(user));
        }
        return listVTOS;
    }
}
