package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service  // 确保有这个注解
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByTel(String usertel) {
        // 保持原有的查询逻辑
        if (usertel == null || usertel.isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        // 去除前后空格
        String trimmedTel = usertel.trim();
        if (!trimmedTel.equals(usertel)) {
            System.out.println("⚠️ 检测到手机号有前后空格，已去除: [" + trimmedTel + "]");
            usertel = trimmedTel;
        }

        return userMapper.findUserByTel(usertel);
    }

    @Override
    public void register(User user) {  // 实现新的方法
        System.out.println("\n===== UserService.register =====");
        System.out.println("注册用户信息:");
        System.out.println("  手机号: [" + user.getUserTel() + "]");
        System.out.println("  昵称: [" + user.getUserName() + "]");
        System.out.println("  性别: [" + user.getUserSex() + "]");
        System.out.println("  密码(MD5): [" + user.getUserPassword() + "]");

        if (user.getUserTel() == null || user.getUserTel().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (user.getUserPassword() == null || user.getUserPassword().isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        String trimmedTel = user.getUserTel().trim();
        if (!trimmedTel.equals(user.getUserTel())) {
            System.out.println("⚠️ 手机号有前后空格，已去除: [" + trimmedTel + "]");
            user.setUserTel(trimmedTel);
        }

        int result = userMapper.insertUser(user);

        if (result > 0) {
            System.out.println("✅ 用户注册成功！");
        } else {
            System.out.println("❌ 用户注册失败");
            throw new RuntimeException("用户注册失败");
        }
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        String usertel = (String) map.get("usertel");
            userMapper.updatePwd(usertel, newPwd);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}