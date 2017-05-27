package com.mobile5.midas.midas_m5.DB;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile5.midas.midas_m5.dto.UserDTO;

public class MySharedPreferences {
    private final String NAME = "user";
    private final String ID = "id";
    private final String PWD = "password";
    private final String AUTO_LOGIN = "auto_login";

    private Context mContext;

    public MySharedPreferences(Context context) {
        mContext = context;
    }

    public void setUserInfo(String id, String pwd) {
        SharedPreferences pref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ID, id);
        editor.putString(PWD, pwd);
        editor.commit();
    }

    public String[] getUserInfo() {
        String[] array = new String[2];
        SharedPreferences pref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        array[0] = pref.getString(ID, "");
        array[1] = pref.getString(PWD, "");
        return array;
    }

    public void setAutoLogin(boolean isAutoLogin) {
        SharedPreferences pref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AUTO_LOGIN, isAutoLogin);
        editor.commit();
    }

    public boolean isAutoLogin() {
        SharedPreferences pref = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(AUTO_LOGIN, false);
    }
}
