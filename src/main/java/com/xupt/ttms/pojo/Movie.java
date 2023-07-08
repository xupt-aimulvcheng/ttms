package com.xupt.ttms.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @TableName movie
 */
@TableName(value ="movie")
public class Movie extends BaseEntity {

    /**
     *
     */
    private String mName;

    /**
     *
     */
    @TableField(exist = false)
    private List<MovieType> mType;

    /**
     *
     */
    private Integer mLength;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date mDate;

    /**
     *
     */
    private String mDirector;

    /**
     *
     */
    private String mActor;

    /**
     *
     */
    @TableField(exist = false)
    private Map<String,Object> mBoxOffice;

    /**
     *
     */
    @TableField(exist = false)
    private Double mScore;

    /**
     *
     */
    private String mIntroduction;

    /**
     *
     */
    private String mImage;

    /**
     *   1:已上映 2:已下线 3:即将上映
     */
    private String status;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public List<MovieType> getmType() {
        return mType;
    }

    public void setmType(List<MovieType> mType) {
        this.mType = mType;
    }

    public Integer getmLength() {
        return mLength;
    }

    public void setmLength(Integer mLength) {
        this.mLength = mLength;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmDirector() {
        return mDirector;
    }

    public void setmDirector(String mDirector) {
        this.mDirector = mDirector;
    }

    public String getmActor() {
        return mActor;
    }

    public void setmActor(String mActor) {
        this.mActor = mActor;
    }

    public Map<String, Object> getmBoxOffice() {
        return mBoxOffice;
    }

    public void setmBoxOffice(Map<String, Object> mBoxOffice) {
        this.mBoxOffice = mBoxOffice;
    }

    public Double getmScore() {
        return mScore;
    }

    public void setmScore(Double mScore) {
        this.mScore = mScore;
    }

    public String getmIntroduction() {
        return mIntroduction;
    }

    public void setmIntroduction(String mIntroduction) {
        this.mIntroduction = mIntroduction;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (!Objects.equals(mName, movie.mName)) return false;
        if (!Objects.equals(mType, movie.mType)) return false;
        if (!Objects.equals(mLength, movie.mLength)) return false;
        if (!Objects.equals(mDate, movie.mDate)) return false;
        if (!Objects.equals(mDirector, movie.mDirector)) return false;
        if (!Objects.equals(mActor, movie.mActor)) return false;
        if (!Objects.equals(mBoxOffice, movie.mBoxOffice)) return false;
        if (!Objects.equals(mScore, movie.mScore)) return false;
        if (!Objects.equals(mIntroduction, movie.mIntroduction))
            return false;
        if (!Objects.equals(mImage, movie.mImage)) return false;
        return Objects.equals(status, movie.status);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + (mLength != null ? mLength.hashCode() : 0);
        result = 31 * result + (mDate != null ? mDate.hashCode() : 0);
        result = 31 * result + (mDirector != null ? mDirector.hashCode() : 0);
        result = 31 * result + (mActor != null ? mActor.hashCode() : 0);
        result = 31 * result + (mBoxOffice != null ? mBoxOffice.hashCode() : 0);
        result = 31 * result + (mScore != null ? mScore.hashCode() : 0);
        result = 31 * result + (mIntroduction != null ? mIntroduction.hashCode() : 0);
        result = 31 * result + (mImage != null ? mImage.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "mName='" + mName + '\'' +
                ", mType=" + mType +
                ", mLength=" + mLength +
                ", mDate=" + mDate +
                ", mDirector='" + mDirector + '\'' +
                ", mActor='" + mActor + '\'' +
                ", mBoxOffice=" + mBoxOffice +
                ", mScore=" + mScore +
                ", mIntroduction='" + mIntroduction + '\'' +
                ", mImage='" + mImage + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}
