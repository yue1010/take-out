package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.UserAdd;
import com.itheima.service.UserAddService;
import com.itheima.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/address")
public class UserAddController {

    @Autowired
    private UserAddService userAddService;

    // ========================
    // 完全对齐你 UserController 的写法
    // 不传token不会报错，会返回“未登录”
    // ========================
    @PostMapping("/add")
    public Result add(
            @RequestParam String receiver,
            @RequestParam(required = false) String reSex,
            @RequestParam String reTel,
            @RequestParam String province,
            @RequestParam String city,
            @RequestParam String region,
            @RequestParam String detailedAdd,
            // 重点：这里改成 非必填！！！
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
        // 1. 校验token（和你userInfo完全一样）
        if (token == null || token.isEmpty()) {
            return Result.error("未登录，请先登录");
        }

        try {
            // 2. 解析token
            Map<String, Object> map = JwtUtil.parseToken(token);
            String usertel = null;

            if (map.containsKey("tel")) {
                usertel = (String) map.get("tel");
            } else if (map.containsKey("usertel")) {
                usertel = (String) map.get("usertel");
            }

            if (usertel == null) {
                return Result.error("token无效");
            }

            // 3. 封装地址
            UserAdd userAdd = new UserAdd();
            userAdd.setReceiver(receiver);
            userAdd.setReSex(reSex);
            userAdd.setReTel(reTel);
            userAdd.setProvince(province);
            userAdd.setCity(city);
            userAdd.setRegion(region);
            userAdd.setDetailedAdd(detailedAdd);

            // 4. 保存
            return userAddService.addAddress(userAdd, usertel);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("token无效或已过期");
        }
    }

    // 👇 只追加这个 GET 接口，原有代码不动
    @GetMapping("/list")
    public Result list(@RequestHeader(name = "Authorization", required = false) String token) {
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

            if (usertel == null) {
                return Result.error("token无效");
            }

            return userAddService.listAddress(usertel);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("token无效或已过期");
        }
    }
}