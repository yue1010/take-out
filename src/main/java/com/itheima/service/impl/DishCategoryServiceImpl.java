package com.itheima.service.impl;

import com.itheima.mapper.DishCategoryMapper;
import com.itheima.pojo.DishCategory;
import com.itheima.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜品分类业务层实现类
 */
@Service
public class DishCategoryServiceImpl implements DishCategoryService {

    @Autowired
    private DishCategoryMapper dishCategoryMapper;

    @Override
    public List<DishCategory> findAllCategories() {
        return dishCategoryMapper.findAllCategories();
    }
}