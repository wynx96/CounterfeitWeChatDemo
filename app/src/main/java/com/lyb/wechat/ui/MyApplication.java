package com.lyb.wechat.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by 18348 on 2016/8/24.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;

    public MyApplication() {
        myApplication = this;
//        LinearLayout.
    }

    public static MyApplication getApplication() {
        return myApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
