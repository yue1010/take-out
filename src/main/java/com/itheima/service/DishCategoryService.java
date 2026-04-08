package com.itheima.service;

import com.itheima.pojo.DishCategory;

import java.util.List;

/**
 * 菜品分类业务层接口
 */
public interface DishCategoryService {

    /**
     * 获取所有菜品分类
     * @return 菜品分类列表
     */
    List<DishCategory> findAllCategories();
}