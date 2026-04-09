package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("Sales")
public class Sales {
    // ✅ 必须加这个注解，告诉MyBatis-Plus这是自增主键，自动回填ID
    @TableId(type = IdType.AUTO)
    private Integer saleId;
    private Integer merId;
    private Integer adrId;
    private Integer userNo;
    private Integer ridNo;
    private LocalDateTime saleTime;
    private BigDecimal totalAmt;
    private Integer status;
}