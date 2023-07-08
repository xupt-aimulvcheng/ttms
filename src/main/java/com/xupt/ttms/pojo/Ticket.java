package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName ticket
 */
@TableName(value ="ticket")
public class Ticket implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Integer seatId;

    /**
     *
     */
    private Integer planId;

    /**
     *
     */
    private BigDecimal ticketPrice;

    /**
     * 含义如下：
            1：待销售
            0：对应的座位已损坏
            2：锁定
            9：卖出
     */
    private Integer ticketStatus;

    /**
     * 加锁时间(下单后加锁)
     */
    private Date ticketLocktime;

    /**
     *
     */
    private Integer orderId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    @TableField(exist = false)
    private Integer seatColumn;

    @TableField(exist = false)
    private Integer seatRow;


    /**
     *
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public Integer getSeatId() {
        return seatId;
    }

    /**
     *
     */
    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    /**
     *
     */
    public Integer getPlanId() {
        return planId;
    }

    /**
     *
     */
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    /**
     *
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     *
     */
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * 含义如下：
            1：待销售
            0：对应的座位已损坏
            2：锁定
            9：卖出
     */
    public Integer getTicketStatus() {
        return ticketStatus;
    }

    /**
     * 含义如下：
            1：待销售
            0：对应的座位已损坏
            2：锁定
            9：卖出
     */
    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    /**
     * 加锁时间(下单后加锁)
     */
    public Date getTicketLocktime() {
        return ticketLocktime;
    }

    /**
     * 加锁时间(下单后加锁)
     */
    public void setTicketLocktime(Date ticketLocktime) {
        this.ticketLocktime = ticketLocktime;
    }

    /**
     *
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     *
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     *
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Ticket other = (Ticket) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSeatId() == null ? other.getSeatId() == null : this.getSeatId().equals(other.getSeatId()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getTicketPrice() == null ? other.getTicketPrice() == null : this.getTicketPrice().equals(other.getTicketPrice()))
            && (this.getTicketStatus() == null ? other.getTicketStatus() == null : this.getTicketStatus().equals(other.getTicketStatus()))
            && (this.getTicketLocktime() == null ? other.getTicketLocktime() == null : this.getTicketLocktime().equals(other.getTicketLocktime()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSeatId() == null) ? 0 : getSeatId().hashCode());
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getTicketPrice() == null) ? 0 : getTicketPrice().hashCode());
        result = prime * result + ((getTicketStatus() == null) ? 0 : getTicketStatus().hashCode());
        result = prime * result + ((getTicketLocktime() == null) ? 0 : getTicketLocktime().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seatId=").append(seatId);
        sb.append(", planId=").append(planId);
        sb.append(", ticketPrice=").append(ticketPrice);
        sb.append(", ticketStatus=").append(ticketStatus);
        sb.append(", ticketLocktime=").append(ticketLocktime);
        sb.append(", orderId=").append(orderId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
