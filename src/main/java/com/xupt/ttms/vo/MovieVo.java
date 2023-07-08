package com.xupt.ttms.vo;/*
 * @author ck
 * @date 2023/5/13
 * @apiNote
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xupt.ttms.pojo.MovieType;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ToString
public class MovieVo {
    private Long id;
    private String mName;
    private List<MovieType> mType;
    private Integer mLength;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date mDate;
    private String mDirector;
    private String mActor;
    private Map<String,Object> mBoxOffice;
    private Double mScore;

    private String mIntroduction;

    private String mImage;

    private String status;

    public MovieVo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
