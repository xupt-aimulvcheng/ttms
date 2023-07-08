package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName video
 */
@TableName(value ="video")
public class Video implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private String des;

    /**
     *
     */
    private Integer mId;

    /**
     *
     */
    private String vSrc;

    /**
     *
     */
    private String iSrc;

    /**
     *
     */
    private Integer len;

    /**
     * 观看数量
     */
    private Integer watchNum;

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
    public String getDes() {
        return des;
    }

    /**
     *
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     *
     */
    public Integer getmId() {
        return mId;
    }

    /**
     *
     */
    public void setmId(Integer mId) {
        this.mId = mId;
    }

    /**
     *
     */
    public String getvSrc() {
        return vSrc;
    }

    /**
     *
     */
    public void setvSrc(String vSrc) {
        this.vSrc = vSrc;
    }

    /**
     *
     */
    public String getiSrc() {
        return iSrc;
    }

    /**
     *
     */
    public void setiSrc(String iSrc) {
        this.iSrc = iSrc;
    }

    /**
     *
     */
    public Integer getLen() {
        return len;
    }

    /**
     *
     */
    public void setLen(Integer len) {
        this.len = len;
    }

    /**
     * 观看数量
     */
    public Integer getWatchNum() {
        return watchNum;
    }

    /**
     * 观看数量
     */
    public void setWatchNum(Integer watchNum) {
        this.watchNum = watchNum;
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
        Video other = (Video) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDes() == null ? other.getDes() == null : this.getDes().equals(other.getDes()))
            && (this.getmId() == null ? other.getmId() == null : this.getmId().equals(other.getmId()))
            && (this.getvSrc() == null ? other.getvSrc() == null : this.getvSrc().equals(other.getvSrc()))
            && (this.getiSrc() == null ? other.getiSrc() == null : this.getiSrc().equals(other.getiSrc()))
            && (this.getLen() == null ? other.getLen() == null : this.getLen().equals(other.getLen()))
            && (this.getWatchNum() == null ? other.getWatchNum() == null : this.getWatchNum().equals(other.getWatchNum()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDes() == null) ? 0 : getDes().hashCode());
        result = prime * result + ((getmId() == null) ? 0 : getmId().hashCode());
        result = prime * result + ((getvSrc() == null) ? 0 : getvSrc().hashCode());
        result = prime * result + ((getiSrc() == null) ? 0 : getiSrc().hashCode());
        result = prime * result + ((getLen() == null) ? 0 : getLen().hashCode());
        result = prime * result + ((getWatchNum() == null) ? 0 : getWatchNum().hashCode());
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
        sb.append(", des=").append(des);
        sb.append(", mId=").append(mId);
        sb.append(", vSrc=").append(vSrc);
        sb.append(", iSrc=").append(iSrc);
        sb.append(", len=").append(len);
        sb.append(", watchNum=").append(watchNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
