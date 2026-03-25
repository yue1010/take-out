package com.itheima.pojo;

import java.time.LocalDateTime;

public class User {
    // 数据库主键是 user_no，所以用它来作为实体类的标识
    private Integer userNo;      // 对应数据库的 user_no (int)
    private String userPassword;  // 对应数据库的 user_password
    private String userName;      // 对应数据库的 user_name
    private String userSex;       // 对应数据库的 user_sex
    private String userTel;       // 对应数据库的 user_tel
    private LocalDateTime createTime;  // 对应数据库的 createTime (datetime)
    private LocalDateTime updateTime;  // 对应数据库的 updateTime (datetime)

    // Getter 和 Setter
    public Integer getUserNo() { return userNo; }
    public void setUserNo(Integer userNo) { this.userNo = userNo; }
    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserSex() { return userSex; }
    public void setUserSex(String userSex) { this.userSex = userSex; }
    public String getUserTel() { return userTel; }
    public void setUserTel(String userTel) { this.userTel = userTel; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}