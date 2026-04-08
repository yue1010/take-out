package com.itheima.pojo;

import java.time.LocalDateTime;
public class Rider {
    private String ridNo;
    private String ridName;
    private String sex;
    private String ridTel;
    private LocalDateTime entryTime;
    private LocalDateTime leaveTime;

    public String getRidNo() {
        return ridNo;
    }

    public void setRidNo(String ridNo) {
        this.ridNo = ridNo;
    }

    public String getRidName() {
        return ridName;
    }

    public void setRidName(String ridName) {
        this.ridName = ridName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRidTel() {
        return ridTel;
    }

    public void setRidTel(String ridTel) {
        this.ridTel = ridTel;
    }
}