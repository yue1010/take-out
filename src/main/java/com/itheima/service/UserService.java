package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {

    //根据手机号查询用户
    public User findUserByTel(String usertel);

    //注册
    public void register(User user);

    //更新用户信息
    void updateByUserTel(String userName, String userSex, String userTel);

    //更新用户密码
    public void updatePwd(String newPwd);
}