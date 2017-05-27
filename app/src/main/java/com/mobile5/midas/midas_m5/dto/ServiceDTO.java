package com.mobile5.midas.midas_m5.dto;

import java.io.Serializable;

public class ServiceDTO implements Serializable{
    private int mId;
    private String mTitle;
    private String mLocation;
    private int mPointPerHour;
    private String mDetail;
    private String mImageUrl;
    private boolean mStete;

    public ServiceDTO() {
        this(-1, null, null, -1, null, null, false);
    }
    public ServiceDTO(int id, String title, String location, int pointPerHour, String detail, String imageUrl, boolean state) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mPointPerHour = pointPerHour;
        mDetail = detail;
        mImageUrl = imageUrl;
        mStete = state;
    }

    public void setId(int id) {
        mId = id;
    }
    public int getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
    public String getLocation() {
        return mLocation;
    }

    public void setPointPerHour(int pointPerHour) {
        mPointPerHour = pointPerHour;
    }
    public int getPointPerHour() {
        return mPointPerHour;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }
    public String getDetail() {
        return mDetail;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setmStete(boolean stete) {
        mStete = stete;
    }
    public boolean getState() {
        return mStete;
    }
}
