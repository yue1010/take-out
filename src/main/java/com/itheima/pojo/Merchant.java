package com.itheima.pojo;

import lombok.Data;

import java.time.LocalTime;

//商家实体
@Data
public class Merchant {
    //商家编号
    private String merId;
    //商家名称
    private String merName;
    //联系电话
    private String merPhone;
    //地址
    private String merAddr;
    //营业开始时间
    private LocalTime merStarttime;
    //营业结束时间
    private LocalTime merEndtime;
}
