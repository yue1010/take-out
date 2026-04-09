package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价实体类
 */
@Data
public class Comment {
    // 评价ID
    private String comId;
    // 订单明细ID
    private String orderId;
    // 商家ID
    private String merId;
    // 骑手ID
    private String ridNo;
    // 用户ID（从JWT获取）
    private Long userId;
    // 评价内容
    private String comCon;
    // 评价时间
    private LocalDateTime comTime;
}