package com.itheima.service.impl;

import com.itheima.mapper.DishMapper;
import com.itheima.pojo.DishQuery;
import com.itheima.pojo.PageResult;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜品业务层实现类
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult findDishByPage(DishQuery query) {
        // 1. 计算分页起始索引（pageNum从1开始）
        Integer start = (query.getPageNum() - 1) * query.getPageSize();
        // 2. 查询符合条件的菜品列表
        var dishList = dishMapper.findDishByCondition(query, start, query.getPageSize());
        // 3. 查询符合条件的总记录数
        Long total = dishMapper.findTotalCount(query);
        // 4. 封装分页结果
        return new PageResult(total, dishList);
    }
}