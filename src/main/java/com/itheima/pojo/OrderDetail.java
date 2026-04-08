package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Order_detail")
public class OrderDetail {
    private Integer orderId;
    // ✅ 统一为 Integer，和 Sales.saleId 类型完全一致
    private Integer saleId;
    private Integer dishDetailId;
    private Integer qty;
    private Float unitPrice;
}