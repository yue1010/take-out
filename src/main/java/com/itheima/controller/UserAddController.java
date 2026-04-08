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
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class UserAddController {

    @Autowired
    private UserAddService userAddService;

    @PostMapping("/add")
    public Result add(
            @RequestParam String receiver,
            @RequestParam(required = false) String reSex,
            @RequestParam String reTel,
            @RequestParam String province,
            @RequestParam String city,
            @RequestParam String region,
            @RequestParam String detailedAdd,
            @RequestHeader(name = "Authorization", required = false) String token
    ) {
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

            UserAdd userAdd = new UserAdd();
            userAdd.setReceiver(receiver);
            userAdd.setReSex(reSex);
            userAdd.setReTel(reTel);
            userAdd.setProvince(province);
            userAdd.setCity(city);
            userAdd.setRegion(region);
            userAdd.setDetailedAdd(detailedAdd);

            return userAddService.addAddress(userAdd, usertel);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("token无效或已过期");
        }
    }

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