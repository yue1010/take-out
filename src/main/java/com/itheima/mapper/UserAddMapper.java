package com.itheima.mapper;

import com.itheima.pojo.UserAdd;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAddMapper {

    /**
     * 新增收货地址
     * @param userAdd 地址对象
     * @return 影响行数
     */
    @Insert("INSERT INTO user_add (receiver, re_sex, re_tel, province, city, region, detailed_add, user_no) " +
            "VALUES (#{receiver}, #{reSex}, #{reTel}, #{province}, #{city}, #{region}, #{detailedAdd}, #{userNo})")
    int insert(UserAdd userAdd);
}