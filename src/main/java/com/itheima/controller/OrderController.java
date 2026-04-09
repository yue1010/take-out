package com.itheima.controller;

import com.itheima.mapper.OrderDetailMapper;
import com.itheima.pojo.OrderDetail;
import com.itheima.pojo.Result;
import com.itheima.pojo.Sales;
import com.itheima.service.OrderService;
import com.itheima.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailMapper orderDetailMapper;

    // ====================== 接口1：创建订单 ======================
    @PostMapping("/create")
    public Result<Sales> create(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        // 从请求头获取token
        String token = request.getHeader("Authorization");

        // 使用你自己的JwtUtil解析用户ID
        Integer userNo = JwtUtil.getUserIdFromToken(token);

        // ✅ 关键：如果用户ID为空，直接抛出错误，不允许创建订单
        if (userNo == null) {
            return Result.error("用户未登录");
        }

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

    @GetMapping("/detail")
    public Result<List<OrderDetail>> detail(
            @RequestParam(required = false) Integer saleId,
            HttpServletRequest request
    ) {
        try {
            if (saleId == null) {
                return Result.error("订单ID不能为空");
            }

            // 获取当前登录用户ID
            String token = request.getHeader("Authorization");
            Integer loginUserId = JwtUtil.getUserIdFromToken(token);

            // 查询订单
            Sales sales = orderService.getById(saleId);
            if (sales == null) {
                return Result.error("订单不存在");
            }

            // ====================== ✅ 关键修复 ======================
            Integer orderUserId = sales.getUserNo();
            if (orderUserId == null) {
                return Result.error("该订单未关联用户，无法查看");
            }
            if (!orderUserId.equals(loginUserId)) {
                return Result.error("您无权查看此订单");
            }

            // 查询订单明细
            List<OrderDetail> items = orderDetailMapper.selectBySaleId(saleId);
            return Result.success(items);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("系统异常：" + e.getMessage());
        }
    }
}