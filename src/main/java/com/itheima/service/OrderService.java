package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.Sales;
import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Sales> {
    /**
     * 创建订单（自动从菜品明细获取商家ID）
     */
    Sales createOrder(Map<String, Object> params, Integer userNo);

    /**
     * 查询当前用户订单列表
     */
    List<Sales> getOrderList(Integer userNo);
}