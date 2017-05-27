package com.mobile5.midas.midas_m5.dto;

public class UserDTO {
    private String mId;
    private String mPassword;
    private int mPoint;
    private String mToken;
    private String mName;

    public UserDTO() {
        this(null, null, -1, null, null);
    }
    public UserDTO(String id, String password, int point, String token, String name) {
        mId = id;
        mPassword = password;
        mPoint = point;
        mToken = token;
        mName = name;
    }

    public void setId(String id) {
        mId = id;
    }
    public String getId() {
        return mId;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
    public String getPassword() {
        return mPassword;
    }

    public void setPoint(int point) {
        mPoint = point;
    }
    public int getPoint() {
        return mPoint;
    }

    public void setToken(String token) {
        mToken = token;
    }
    public String getToken() {
        return mToken;
    }

    public void setName(String name) {
        mName = name;
    }
    public String getName() {
        return mName;
    }
}
