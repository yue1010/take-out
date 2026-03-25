package com.itheima.service.impl;

import com.itheima.mapper.UserAddMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.pojo.UserAdd;
import com.itheima.pojo.Result;
import com.itheima.service.UserAddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAddServiceImpl implements UserAddService {

    @Autowired
    private UserAddMapper userAddMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result addAddress(UserAdd userAdd, String usertel) {
        // 1. 根据登录手机号获取用户
        User user = userMapper.findUserByTel(usertel);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 2. 绑定登录用户（关键）
        userAdd.setUserNo(user.getUserNo());

        // 3. 保存地址
        // adr_id 是 int 自增 → 数据库自动生成！不用管！
        userAddMapper.insert(userAdd);

        return Result.success("添加地址成功");
    }
}