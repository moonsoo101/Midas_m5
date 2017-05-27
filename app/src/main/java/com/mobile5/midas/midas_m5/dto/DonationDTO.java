package com.mobile5.midas.midas_m5.dto;

public class DonationDTO {
    private int mId;
    private String mPlaceName;
    private String mImageUrl;
    private int mTotalPoint;

    public DonationDTO() {
        this(-1, null, null, -1);
    }
    public DonationDTO(int id, String placeName, String imageUrl, int totalPoint) {
        mId = id;
        mPlaceName = placeName;
        mImageUrl = imageUrl;
        mTotalPoint = totalPoint;
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
}
