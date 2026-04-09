package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.DishDetailMapper;
import com.itheima.mapper.OrderDetailMapper;
import com.itheima.mapper.SalesMapper;
import com.itheima.pojo.DishDetail;
import com.itheima.pojo.OrderDetail;
import com.itheima.pojo.Sales;
import com.itheima.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<SalesMapper, Sales> implements OrderService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private DishDetailMapper dishDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Sales createOrder(Map<String, Object> params, Integer userNo) {

        // 1. 安全获取参数
        Integer adrId = (Integer) params.get("adrId");
        List<Map<String, Object>> items = (List<Map<String, Object>>) params.get("orderItems");

        // 2. 获取菜品明细
        Integer dishDetailId = (Integer) items.get(0).get("dishDetailId");
        DishDetail dishDetail = dishDetailMapper.getDishDetail(dishDetailId);
        if (dishDetail == null) {
            throw new RuntimeException("菜品不存在");
        }
        Integer merId = dishDetail.getMerId();

        // 3. 创建订单主表
        Sales sales = new Sales();
        sales.setMerId(merId);
        sales.setAdrId(adrId);
        sales.setUserNo(userNo);
        sales.setSaleTime(LocalDateTime.now());
        sales.setTotalAmt(BigDecimal.ZERO);

        // 4. 保存订单（@TableId注解会自动回填自增saleId）
        boolean saveSuccess = this.save(sales);
        if (!saveSuccess) {
            throw new RuntimeException("订单创建失败");
        }
        Integer saleId = sales.getSaleId(); // ✅ 现在一定能拿到ID，不会为null

        // 5. 保存订单明细（强制赋值saleId，确保不为空）
        for (Map<String, Object> item : items) {
            OrderDetail detail = new OrderDetail();
            // ✅ 强制赋值，确保saleId不为空
            detail.setSaleId(saleId);
            detail.setDishDetailId((Integer) item.get("dishDetailId"));
            detail.setQty((Integer) item.get("qty"));
            detail.setUnitPrice(dishDetail.getUnitPrice());

            // 插入明细
            orderDetailMapper.insert(detail);
        }

        return sales;
    }

    @Override
    public List<Sales> getOrderList(Integer userNo) {
        LambdaQueryWrapper<Sales> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Sales::getUserNo, userNo);
        return this.list(wrapper);
    }
}