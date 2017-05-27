package com.mobile5.midas.midas_m5.dto;

public class ServiceDTO {
    private int mId;
    private String mPlaceName;
    private int mPointPerHour;
    private String mDetail;
    private String mImageUrl;
    private boolean mStete;

    public ServiceDTO() {
        this(-1, null, -1, null, null, false);
    }
    public ServiceDTO(int id, String placeName, int pointPerHour, String detail, String imageUrl, boolean state) {
        mId = id;
        mPlaceName = placeName;
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

    public void setPlaceName(String placeName) {
        mPlaceName = placeName;
    }
    public String getPlaceName() {
        return mPlaceName;
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
