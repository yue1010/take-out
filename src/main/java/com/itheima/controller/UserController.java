package com.itheima.controller;

import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@TableName("users")  // 你的表名叫 users！
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
    public Result<String> login(
            @Pattern(regexp = "^1[3-9]\\d{9}$") String usertel,
            @Pattern(regexp = "^\\S{5,16}$") String password
    ) {
        User u = userService.findUserByTel(usertel);

        if (u == null) {
            return Result.error("手机号错误");
        }

        String encryptedPassword = Md5Util.getMD5String(password);

        if (encryptedPassword.equals(u.getUserPassword())) {
            Map<String, Object> claims = new HashMap<>();

            claims.put("usertel", u.getUserTel());
            claims.put("userNo", u.getUserNo());
            String token = JwtUtil.genToken(claims);
            System.out.println("登录成功，token：" + token);
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
            Map<String, Object> map = JwtUtil.parseToken(token);

            String usertel = null;

            if (map.containsKey("tel")) {
                usertel = (String) map.get("tel");
            } else if (map.containsKey("usertel")) {
                usertel = (String) map.get("usertel");
            }

            if (usertel == null || usertel.isEmpty()) {
                return Result.error("token中未包含手机号信息");
            }

            User user = userService.findUserByTel(usertel);

            if (user == null) {
                return Result.error("用户不存在");
            }

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

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");

        if (old_pwd == null || old_pwd.isBlank() ||
                new_pwd == null || new_pwd.isBlank() ||
                re_pwd == null || re_pwd.isBlank()) {
            return Result.error("参数不能为空");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        if (map == null) {
            return Result.error("请先登录！");
        }

        String usertel = (String) map.get("usertel");
        User loginUser = userService.findUserByTel(usertel);

        if (!loginUser.getUserPassword().equals(Md5Util.getMD5String(old_pwd))) {
            return Result.error("原密码填写不正确");
        }

        if (!new_pwd.equals(re_pwd)) {
            return Result.error("两次填写的新密码不相同");
        }

        userService.updatePwd(Md5Util.getMD5String(new_pwd));
        return Result.success();
    }
    // ========== 调试接口结束 ==========
}
