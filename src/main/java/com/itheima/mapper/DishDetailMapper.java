package com.itheima.mapper;

import com.itheima.pojo.DishDetail;
import com.itheima.pojo.Ingredient;
import com.itheima.pojo.Ingredient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DishDetailMapper {

    // 根据 dish_detail_id 查询菜品详情（多表联查）
    // 把参数名从 dish_detail_id 改为 dishDetailId（和驼峰一致）
    @Select("SELECT " +
            "dd.dish_detail_id, " +
            "dd.dish_id, " +
            "dd.unit_price, " +
            "dd.stock_qty, " +
            "dd.mer_id, " +
            "d.dish_name, " +
            "d.dish_category, " +
            "c.category_name, " +
            "m.mer_name " +
            "FROM Dish_detail dd " +
            "LEFT JOIN Dish d ON dd.dish_id = d.dish_id " +
            "LEFT JOIN dish_categories c ON d.dish_category = c.dish_category " +
            "LEFT JOIN Merchant m ON dd.mer_id = m.mer_id " +
            "WHERE dd.dish_detail_id = #{dishDetailId}") // 改：dish_detail_id → dishDetailId
    DishDetail getDishDetail(Integer dishDetailId); // 方法参数名也改

    // 食材查询同理
    @Select("SELECT " +
            "i.ingr_name, " +
            "id.quantity " +
            "FROM Ingredient_dish id " +
            "LEFT JOIN Ingredient i ON id.ingr_id = i.ingr_id " +
            "WHERE id.dish_id = #{dishId}") // 改：dish_id → dishId
    List<Ingredient> getIngredientsByDishId(Integer dishId); // 方法参数名改


}