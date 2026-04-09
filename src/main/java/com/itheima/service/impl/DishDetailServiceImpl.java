package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mapper.DishDetailMapper;
import com.itheima.pojo.DishDetail;
import com.itheima.service.DishDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishDetailServiceImpl extends ServiceImpl<DishDetailMapper, DishDetail> implements DishDetailService {

    @Autowired
    private DishDetailMapper dishDetailMapper;

    @Override
    // 修正1：参数名改为驼峰（dishDetailId），符合规范
    public DishDetail getDishDetail(Integer dishDetailId) {
        // 查询基础信息（参数名同步修改）
        DishDetail detail = dishDetailMapper.getDishDetail(dishDetailId);
        if (detail == null) {
            return null;
        }
        // 修正2：调用驼峰格式的getter方法（getDishId()），替代原有的getDish_id()
        detail.setIngredients(dishDetailMapper.getIngredientsByDishId(detail.getDishId()));
        return detail;
    }
}