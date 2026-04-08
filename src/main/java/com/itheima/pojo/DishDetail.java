package com.itheima.pojo;

import lombok.Data;
import java.util.List;

@Data
public class DishDetail {
    // 核心修改：下划线字段 → 驼峰字段（匹配MyBatis的驼峰映射）
    private Integer dishDetailId;      // 对应 dish_detail_id
    private Integer dishId;            // 对应 dish_id
    private float unitPrice;           // 对应 unit_price
    private Integer stockQty;          // 对应 stock_qty
    private Integer merId;             // 对应 mer_id
    private String dishName;           // 对应 dish_name
    private String dishCategory;       // 对应 dish_category
    private String categoryName;       // 对应 category_name
    private String merName;            // 对应 mer_name
    private List<Ingredient> ingredients; // 食材列表
}