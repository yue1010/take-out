package com.itheima.mapper;

import com.itheima.pojo.UserAdd;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    // 👇 只追加这一段，原有代码不动
    // 调试查询 - 获取当前用户地址列表（完全对齐你的风格）
    @Select("SELECT adr_id AS adrId, " +
            "receiver AS receiver, " +
            "re_sex AS reSex, " +
            "re_tel AS reTel, " +
            "province AS province, " +
            "city AS city, " +
            "region AS region, " +
            "detailed_add AS detailedAdd, " +
            "user_no AS userNo " +
            "FROM user_add WHERE user_no = #{userNo}")
    List<UserAdd> selectListByUserNo(Integer userNo);
}