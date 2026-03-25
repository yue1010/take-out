package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {

    //根据手机号查询用户
    public User findUserByTel(String usertel);

    //注册
    public void register(User user);
}