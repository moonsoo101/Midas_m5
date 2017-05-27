package com.mobile5.midas.midas_m5.dto;

import java.io.Serializable;

public class MyDonationDTO implements Serializable{
    private int mId;
    private String mTitle;
    private String mLocation;
    private String mImageUrl;
    private int myPoint;
    private int mTotalPoint;
    private boolean mState;

    public MyDonationDTO(int id, String title, String location, String imageUrl, int totalPoint, int myPoint, boolean mState) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mImageUrl = imageUrl;
        mTotalPoint = totalPoint;
        this.mState = mState;
        this.myPoint = myPoint;
    }

    public int getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(int myPoint) {
        this.myPoint = myPoint;
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
