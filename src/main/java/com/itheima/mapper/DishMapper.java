package com.itheima.mapper;

import com.itheima.pojo.Dish;
import com.itheima.pojo.DishQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品数据访问层
 */
@Mapper
public interface DishMapper {

    /**
     * 多条件查询菜品列表（分页）
     * @param query 查询条件
     * @param start 分页起始索引
     * @param pageSize 每页条数
     * @return 菜品列表
     */
    List<Dish> findDishByCondition(
            @Param("query") DishQuery query,
            @Param("start") Integer start,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 查询符合条件的总记录数
     * @param query 查询条件
     * @return 总记录数
     */
    Long findTotalCount(@Param("query") DishQuery query);
}