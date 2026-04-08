package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.Sales;
import com.itheima.service.OrderService;
import com.itheima.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

// 把 javax 全部改成 jakarta
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    // ====================== 接口1：创建订单 ======================
    @PostMapping("/create")
    public Result<Sales> create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 使用你自己的JwtUtil解析用户ID
        Integer userNo = JwtUtil.getUserIdFromToken(token);

        Sales sales = orderService.createOrder(params, userNo);
        return Result.success(sales);
    }

    // ====================== 接口2：订单列表 ======================
    @GetMapping("/list")
    public Result<List<Sales>> list(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Integer userNo = JwtUtil.getUserIdFromToken(token);

        List<Sales> list = orderService.getOrderList(userNo);
        return Result.success(list);
    }
}