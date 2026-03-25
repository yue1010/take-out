import java.time.LocalDateTime;
public class UserAdd {
    private String adrId;
    private String receiver;
    private String sex;
    private String ridTel;
    private String city;
    private String region;
    private String detailedAdd;
    private String userNo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public String getAdrId() {
        return adrId;
    }

    public void setAdrId(String adrId) {
        this.adrId = adrId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetailedAdd() {
        return detailedAdd;
    }

    public void setDetailedAdd(String detailedAdd) {
        this.detailedAdd = detailedAdd;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}