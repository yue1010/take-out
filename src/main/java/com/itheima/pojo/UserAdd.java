package com.itheima.pojo;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserAdd implements Serializable {
    // 地址编号（char(10)）
    private String adrId;
    // 收货人
    private String receiver;
    // 收货人性别
    private String reSex;
    // 联系电话
    private String reTel;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区县
    private String region;
    // 详细地址
    private String detailedAdd;
    // 所属用户编号（外键）
    private Integer userNo;
}