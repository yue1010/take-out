import java.time.LocalDateTime;

/**
 * 销售订单实体类
 * 存储销售订单的核心信息
 *
 * @author 开发者
 * @date 2026/03/14
 */
public class Sales {
    // 销售订单ID
    private String saleId;
    // 订单总金额
    private Double totalAmt;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 商户ID
    private String merId;
    // 用户编号
    private String userNo;
    // 收货地址ID（推测ridNo为收货地址相关，补充注释）
    private String ridNo;
    // 地址ID
    private String adrId;
    // 销售时间（补充缺失的成员变量，并统一为LocalDateTime类型）
    private LocalDateTime saleTime;

    // 获取销售订单ID
    public String getSaleId() {
        return saleId;
    }

    // 设置销售订单ID
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    // 获取订单总金额
    public Double getTotalAmt() {
        return totalAmt;
    }

    // 设置订单总金额
    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    // 获取创建时间
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    // 设置创建时间
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    // 获取更新时间
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    // 设置更新时间
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    // 获取销售时间
    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    // 设置销售时间
    public void setSaleTime(LocalDateTime saleTime) {
        this.saleTime = saleTime;
    }

    // 获取商户ID
    public String getMerId() {
        return merId;
    }

    // 设置商户ID
    public void setMerId(String merId) {
        this.merId = merId;
    }

    // 获取用户编号
    public String getUserNo() {
        return userNo;
    }

    // 设置用户编号
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    // 获取收货地址ID
    public String getRidNo() {
        return ridNo;
    }

    // 设置收货地址ID
    public void setRidNo(String ridNo) {
        this.ridNo = ridNo;
    }

    // 获取地址ID
    public String getAdrId() {
        return adrId;
    }

    // 设置地址ID
    public void setAdrId(String adrId) {
        this.adrId = adrId;
    }
}