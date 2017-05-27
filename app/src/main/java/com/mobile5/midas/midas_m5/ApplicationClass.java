package com.mobile5.midas.midas_m5;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by wisebody on 2017. 5. 27..
 */

public class ApplicationClass extends Application {
    String ID;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * onConfigurationChanged()
     * 컴포넌트가 실행되는 동안 단말의 화면이 바뀌면 시스템이 실행 한다.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
