package com.mobile5.midas.midas_m5.dto;


public class MyListDTO {
    private int mID;
    private String mTitle;
    private String mImageUrl;
    private int mPoint;
    private String mLocation;
    private String mDescription;
    private boolean mState;

    public MyListDTO(int id, String title, int point, String imageUrl, String location, String description, boolean state) {
        mID = id;
        mTitle = title;
        mImageUrl = imageUrl;
        mPoint = point;

        mLocation = location;
        mDescription = description;
        mState = state;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean ismState() {
        return mState;
    }

    public void setmState(boolean mState) {
        this.mState = mState;
    }
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public int getmPoint() {
        return mPoint;
    }

    public void setmPoint(int mPoint) {
        this.mPoint = mPoint;
    }
}
