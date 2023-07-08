package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName plan
 */
@TableName(value ="plan")
public class Plan implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String pName;

    /**
     *
     */
    private Long mId;

    /**
     *
     */
    private Date pStarttime;

    /**
     *
     */
    private Integer hId;

    /**
     *
     */
    private Date pEndtime;

    /**
     * 1代表上线，0代表下线
     */
    private Integer status;

    /**
     *
     */
    private Double price;

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
    private String mImage;

    @TableField(exist = false)
    private String mName;

    @TableField(exist = false)
    private String hName;
    @TableField(exist = false)
    private List<MovieType> mType;
    @TableField(exist = false)
    private Integer mLength;
    @TableField(exist = false)
    private Integer hMax;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getmLength() {
        return mLength;
    }

    public void setmLength(Integer mLength) {
        this.mLength = mLength;
    }

    public List<MovieType> getmType() {
        return mType;
    }

    public void setmType(List<MovieType> mType) {
        this.mType = mType;
    }

    public Integer gethMax() {
        return hMax;
    }

    public void sethMax(Integer hMax) {
        this.hMax = hMax;
    }

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
    public String getpName() {
        return pName;
    }

    /**
     *
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     *
     */
    public Long getmId() {
        return mId;
    }

    /**
     *
     */
    public void setmId(Long mId) {
        this.mId = mId;
    }

    /**
     *
     */
    public Date getpStarttime() {
        return pStarttime;
    }

    /**
     *
     */
    public void setpStarttime(Date pStarttime) {
        this.pStarttime = pStarttime;
    }

    /**
     *
     */
    public Integer gethId() {
        return hId;
    }

    /**
     *
     */
    public void sethId(Integer hId) {
        this.hId = hId;
    }

    /**
     *
     */
    public Date getpEndtime() {
        return pEndtime;
    }

    /**
     *
     */
    public void setpEndtime(Date pEndtime) {
        this.pEndtime = pEndtime;
    }

    /**
     * 1代表上线，0代表下线
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1代表上线，0代表下线
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     */
    public void setPrice(Double price) {
        this.price = price;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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
        Plan other = (Plan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getpName() == null ? other.getpName() == null : this.getpName().equals(other.getpName()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getpStarttime() == null ? other.getpStarttime() == null : this.getpStarttime().equals(other.getpStarttime()))
            && (this.gethId() == null ? other.gethId() == null : this.gethId().equals(other.gethId()))
            && (this.getpEndtime() == null ? other.getpEndtime() == null : this.getpEndtime().equals(other.getpEndtime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getpName() == null) ? 0 : getpName().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getpStarttime() == null) ? 0 : getpStarttime().hashCode());
        result = prime * result + ((gethId() == null) ? 0 : gethId().hashCode());
        result = prime * result + ((getpEndtime() == null) ? 0 : getpEndtime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
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
        sb.append(", pName=").append(pName);
        sb.append(", mId=").append(mId);
        sb.append(", pStarttime=").append(pStarttime);
        sb.append(", hId=").append(hId);
        sb.append(", pEndtime=").append(pEndtime);
        sb.append(", status=").append(status);
        sb.append(", price=").append(price);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
