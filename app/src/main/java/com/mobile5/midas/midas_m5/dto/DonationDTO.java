package com.mobile5.midas.midas_m5.dto;

import java.io.Serializable;

public class DonationDTO implements Serializable{
    private int mId;
    private String mTitle;
    private String mLocation;
    private String mImageUrl;
    private int mTotalPoint;
    private boolean mState;

    public DonationDTO() {
        this(-1, null, null, null, -1);
    }
    public DonationDTO(int id, String title, String location, String imageUrl, int totalPoint) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mImageUrl = imageUrl;
        mTotalPoint = totalPoint;
        mState = true;
    }
    public DonationDTO(int id, String title, String location, String imageUrl, int totalPoint, boolean state) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mImageUrl = imageUrl;
        mTotalPoint = totalPoint;
        mState = state;
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

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setTotalPoint(int totalPoint) {
        mTotalPoint = totalPoint;
    }
    public int getTotalPoint() {
        return mTotalPoint;
    }

    public void setState(boolean state) {
        mState = state;
    }
    public boolean getState() {
        return mState;
    }
}
