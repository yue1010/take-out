package com.itheima.pojo;

import java.time.LocalDateTime;

/**
 * 菜品分类实体类
 */
public class DishCategory {
    // 分类编码
    private String dishCategory;
    // 分类名称
    private String categoryName;

    // Getter 和 Setter 方法
    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}