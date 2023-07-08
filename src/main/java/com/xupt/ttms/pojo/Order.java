package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * order
 * @TableName t_order
 */
@Component
@TableName(value ="t_order")
@EqualsAndHashCode
public class Order implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * orderNo
     */
    private String orderNo;

    /**
     * 0:"待支付"，1:"用户支付票价"，2::"售票员已代为支付票价"，3:“用户已退票“,4:"售票员已退票",-1:"已过期"
     */
    private Integer oStatus;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * desc
     */
    private String des;

    /**
     *
     */
    private Long userId;

    /**
     * 订单生成时间
     */
    private Date generateTime;

    /**
     * 订单支付时间
     */
    private Date purchaseTime;

    /**
     * 订单超时时间
     */
    private Date expireTime;


    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 电影id
     */
    private Integer mId;

    @TableField(exist = false)
    private List<Seat> seats;

    @TableField(exist = false)
    private String mName;
    @TableField(exist = false)
    private String startDate;
    @TableField(exist = false)
    private String hName;
    @TableField(exist = false)
    private String mImage;
    @TableField(exist = false)
    private Integer pId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 0:"待支付"，1:"用户支付票价"，2::"售票员已代为支付票价"，3:“用户已退票“,4:"售票员已退票",-1:"已过期"
     */
    public Integer getoStatus() {
        return oStatus;
    }

    /**
     * 0:"待支付"，1:"用户支付票价"，2::"售票员已代为支付票价"，3:“用户已退票“,4:"售票员已退票",-1:"已过期"
     */
    public void setoStatus(Integer oStatus) {
        this.oStatus = oStatus;
    }

    /**
     * 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * desc
     */
    public String getDes() {
        return des;
    }

    /**
     * desc
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     *
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 订单生成时间
     */
    public Date getGenerateTime() {
        return generateTime;
    }

    /**
     * 订单生成时间
     */
    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }

    /**
     * 订单支付时间
     */
    public Date getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * 订单支付时间
     */
    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    /**
     * 订单超时时间
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 订单超时时间
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 电影id
     */
    public Integer getmId() {
        return mId;
    }

    /**
     * 电影id
     */
    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
