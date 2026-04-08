package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.DishCategoryService;
import com.itheima.service.DishService;
import com.itheima.service.DishDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜品相关控制器（适配你的Result类 + 保留原有分类接口 + 新增菜品详情接口）
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    // 原有菜品分类Service
    @Autowired
    private DishCategoryService dishCategoryService;

    // 原有菜品列表Service
    @Autowired
    private DishService dishService;

    // 新增：菜品详情Service
    @Autowired
    private DishDetailService dishDetailService;

    /**
     * 原有接口：获取所有菜品分类列表
     */
    @GetMapping("/categories")
    public Result<List<DishCategory>> getDishCategories() {
        try {
            List<DishCategory> categories = dishCategoryService.findAllCategories();
            return Result.success(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取菜品分类失败");
        }
    }

    /**
     * 原有接口：菜品列表（多条件分页查询）
     */
    @GetMapping("/list")
    public Result<PageResult> getDishList(DishQuery query) {
        try {
            if (query.getPageNum() == null || query.getPageNum() <= 0) {
                return Result.error("当前页码必须为正整数");
            }
            if (query.getPageSize() == null || query.getPageSize() <= 0) {
                return Result.error("每页条数必须为正整数");
            }
            if (query.getMaxPrice() != null && query.getMinPrice() != null) {
                if (query.getMaxPrice() <= query.getMinPrice()) {
                    return Result.error("最高价必须大于最低价");
                }
            }
            PageResult pageResult = dishService.findDishByPage(query);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询菜品列表失败");
        }
    }

    // ===================== 【已修复：获取菜品详情接口】 =====================
    /**
     * 4.3 获取菜品详情
     * 请求路径：/dish/detail
     * 请求方式：GET
     * 参数：dish_detail_id
     */
    @GetMapping("/detail")
    public Result getDishDetail(
            @RequestParam(name = "dish_detail_id", required = false) Integer dish_detail_id
    ) {
        try {
            if (dish_detail_id == null || dish_detail_id <= 0) {
                return Result.error("菜品明细编号不能为空");
            }

            // 新增：打印传入的参数（关键日志）
            System.out.println("【菜品详情接口】传入的dish_detail_id：" + dish_detail_id);
            // 调用Service查询（参数名同步修改为驼峰，不影响前端传参）
            DishDetail detail = dishDetailService.getDishDetail(dish_detail_id);
            // 新增：打印Service返回的结果（核心日志）
            System.out.println("【菜品详情接口】Service返回的detail：" + detail);

            return Result.success(detail);
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常（如果有）
            return Result.error("获取菜品详情失败");
        }
    }
}