package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.DishDetail;

public interface DishDetailService extends IService<DishDetail> {
    /**
     * 根据菜品详情ID查询完整菜品信息（含配料）
     * @param dishDetailId 菜品详情ID（驼峰命名，和实现类统一）
     * @return 菜品详情
     */
    DishDetail getDishDetail(Integer dishDetailId);
}