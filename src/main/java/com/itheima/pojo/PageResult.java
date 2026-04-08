package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装类（作为Result的data字段值）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {
    // 总记录数（接口文档要求的total）
    private Long total;
    // 菜品列表（接口文档要求的items）
    private List<?> items;
}