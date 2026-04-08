package com.itheima.pojo;

import lombok.Data;

/**
 * 菜品实体类（匹配数据库dish表 + 接口响应字段）
 */
@Data
public class Dish {
    // 菜品编号（数据库dish_id，int自增）
    private Integer dishId;
    // 菜品名称
    private String dishName;
    // 菜品分类编码
    private String dishCategory;
    // 分类名称（关联dish_categories表，接口响应需要）
    private String categoryName;
    // 单价（关联dish_detail表）
    private Float unitPrice;
    // 库存数量（关联dish_detail表）
    private Integer stockQty;
    // 商家编号（关联dish_detail表）
    private String merId;
    // 商家名称（接口响应需要，需关联商家表，这里先预留）
    private String merName;
    // 菜品明细编号（关联dish_detail表）
    private String dishDetailId;
}