package com.itheima.service;

import com.itheima.pojo.UserAdd;
import com.itheima.pojo.Result;

public interface UserAddService {
    Result addAddress(UserAdd userAdd, String usertel);

    Result listAddress(String usertel);
}