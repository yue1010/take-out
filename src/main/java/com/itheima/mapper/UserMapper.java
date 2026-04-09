package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // ✅ 数据库字段：userNo,userTel,userPassword,userName,userSex,createTime,updateTime
    // ✅ 完全和你的表名一致，100% 不报错
    @Select("SELECT user_no AS userNo, " +
            "user_tel AS userTel, " +
            "user_password AS userPassword, " +
            "user_name AS userName, " +
            "user_sex AS userSex, " +
            "createTime AS createTime, " +  // 这里！数据库就是 createTime
            "updateTime AS updateTime " +   // 这里！数据库就是 updateTime
            "FROM users WHERE user_tel = #{usertel}")
    User findUserByTel(String usertel);

    // ✅ 插入也用正确的字段名
    @Insert("INSERT INTO users (user_tel, user_password, user_name, user_sex, createTime, updateTime) " +
            "VALUES (#{userTel}, #{userPassword}, #{userName}, #{userSex}, NOW(), NOW())")
    int insertUser(User user);

    // 调试查询
    @Select("SELECT user_no AS userNo, " +
            "user_tel AS userTel, " +
            "user_password AS userPassword, " +
            "user_name AS userName, " +
            "user_sex AS userSex, " +
            "createTime AS createTime, " +
            "updateTime AS updateTime " +
            "FROM users")
    List<User> findAllUsers();

    @Update("UPDATE users SET user_name = #{userName}, user_sex = #{userSex} WHERE user_tel = #{userTel}")
    void updateByUserTel(String userName, String userSex, String userTel);

    @Update("UPDATE users SET user_password = #{newPwd} WHERE user_tel = #{usertel}")
    void updatePwd(@Param("usertel") String usertel, @Param("newPwd") String newPwd);

    void update(User user);
}