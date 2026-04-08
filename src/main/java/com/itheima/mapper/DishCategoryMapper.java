package com.itheima.mapper;

import com.itheima.pojo.DishCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品分类数据访问层
 */
@Mapper
public interface DishCategoryMapper {

    /**
     * 获取所有菜品分类
     * @return 菜品分类列表
     */
    @Select("SELECT dish_category AS dishCategory, " +
            "category_name AS categoryName, " +
            "createTime AS createTime, " +
            "updateTime AS updateTime " +
            "FROM dish_categories")
    List<DishCategory> findAllCategories();
}