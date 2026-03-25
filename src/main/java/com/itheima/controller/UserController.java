package com.itheima.controller;

import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.pojo.Result;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(
            @RequestParam String usertel,
            @RequestParam String password,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userSex
    ) {
        // 验证手机号
        if (usertel == null || !usertel.matches("^1[3-9]\\d{9}$")) {
            return Result.error("手机号格式不正确");
        }
        // 验证密码
        if (password == null || password.length() < 5) {
            return Result.error("密码长度不能小于5位");
        }

        // 检查手机号是否存在
        User exist = userService.findUserByTel(usertel);
        if (exist != null) {
            return Result.error("手机号已被注册");
        }

        User user = new User();
        user.setUserTel(usertel);
        user.setUserPassword(Md5Util.getMD5String(password));

        // 关键：能收到就存，收不到就填空，绝不报错
        user.setUserName(userName == null ? "" : userName);
        user.setUserSex(userSex == null ? "" : userSex);

        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^1[3-9]\\d{9}$") String usertel,
                                @Pattern(regexp = "^\\S{5,16}$") String password) {
        User u = userService.findUserByTel(usertel);

        if (u == null) {
            return Result.error("手机号错误");
        }

        String encryptedPassword = Md5Util.getMD5String(password);

        if (u.getUserPassword() != null && encryptedPassword.equals(u.getUserPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("tel", u.getUserTel());
            // 同时存入 usertel，兼容两种写法
            claims.put("usertel", u.getUserTel());

            String token = JwtUtil.genToken(claims);

            // 调试：打印生成的token内容
            System.out.println("手机号: " + u.getUserTel());
            System.out.println("Token: " + token);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/test-md5")
    public Result testMd5(@RequestParam String password) {
        String encrypted = Md5Util.getMD5String(password);
        return Result.success(encrypted);
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization", required = false) String token) {
        // 检查 token
        if (token == null || token.isEmpty()) {
            return Result.error("未登录，请先登录");
        }

        try {
            // 解析 token
            Map<String, Object> map = JwtUtil.parseToken(token);

            // 获取手机号 - 支持多种 key
            String usertel = null;

            if (map.containsKey("tel")) {
                usertel = (String) map.get("tel");
            } else if (map.containsKey("usertel")) {
                usertel = (String) map.get("usertel");
            }

            // 验证手机号
            if (usertel == null || usertel.isEmpty()) {
                return Result.error("token中未包含手机号信息");
            }

            // 查询用户
            User user = userService.findUserByTel(usertel);

            if (user == null) {
                return Result.error("用户不存在");
            }

            // 清除密码
            user.setUserPassword(null);

            return Result.success(user);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("token无效或已过期");
        }
    }

    // ========== 调试接口 ==========
    @GetMapping("/debug/token")
    public Result debugToken(@RequestHeader(name = "Authorization", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return Result.error("token为空");
        }

        try {
            // 解析 token
            Map<String, Object> claims = JwtUtil.parseToken(token);

            // 打印所有内容到控制台
            System.out.println("===== Token 内容 =====");
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            // 返回给前端
            return Result.success(claims);
        } catch (Exception e) {
            return Result.error("token解析失败: " + e.getMessage());
        }
    }
    // ========== 调试接口结束 ==========
}