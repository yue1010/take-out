package com.itheima.service;

import com.itheima.pojo.DishQuery;
import com.itheima.pojo.PageResult;

/**
 * 菜品业务层接口
 */
public interface DishService {

    /**
     * 多条件分页查询菜品列表
     * @param query 查询条件
     * @return 分页结果（total+items）
     */
    PageResult findDishByPage(DishQuery query);
}