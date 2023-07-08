package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName seat
 */
@TableName(value ="seat")
public class Seat implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer studioId;

    /**
     * 
     */
    private Integer seatRow;

    /**
     * 
     */
    private Integer seatColumn;

    /**
     * 
     */
    private Integer seatStatus;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public Integer getStudioId() {
        return studioId;
    }

    /**
     * 
     */
    public void setStudioId(Integer studioId) {
        this.studioId = studioId;
    }

    /**
     * 
     */
    public Integer getSeatRow() {
        return seatRow;
    }

    /**
     * 
     */
    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    /**
     * 
     */
    public Integer getSeatColumn() {
        return seatColumn;
    }

    /**
     * 
     */
    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }

    /**
     * 
     */
    public Integer getSeatStatus() {
        return seatStatus;
    }

    /**
     * 
     */
    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
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
        Seat other = (Seat) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStudioId() == null ? other.getStudioId() == null : this.getStudioId().equals(other.getStudioId()))
            && (this.getSeatRow() == null ? other.getSeatRow() == null : this.getSeatRow().equals(other.getSeatRow()))
            && (this.getSeatColumn() == null ? other.getSeatColumn() == null : this.getSeatColumn().equals(other.getSeatColumn()))
            && (this.getSeatStatus() == null ? other.getSeatStatus() == null : this.getSeatStatus().equals(other.getSeatStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStudioId() == null) ? 0 : getStudioId().hashCode());
        result = prime * result + ((getSeatRow() == null) ? 0 : getSeatRow().hashCode());
        result = prime * result + ((getSeatColumn() == null) ? 0 : getSeatColumn().hashCode());
        result = prime * result + ((getSeatStatus() == null) ? 0 : getSeatStatus().hashCode());
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
        sb.append(", studioId=").append(studioId);
        sb.append(", seatRow=").append(seatRow);
        sb.append(", seatColumn=").append(seatColumn);
        sb.append(", seatStatus=").append(seatStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}