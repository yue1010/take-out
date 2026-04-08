package com.itheima.pojo;

import lombok.Data;

/**
 * 菜品查询条件封装类（匹配接口的queryString参数）
 */
@Data
public class DishQuery {
    // 当前页码（必须）
    private Integer pageNum;
    // 每页条数（必须）
    private Integer pageSize;
    // 商家编号（非必须）
    private String merId;
    // 菜品分类编码（非必须）
    private String dishCategory;
    // 菜品名称（非必须，模糊查询）
    private String dishName;
    // 最低价（非必须）
    private Float minPrice;
    // 最高价（非必须，需大于最低价）
    private Float maxPrice;
}