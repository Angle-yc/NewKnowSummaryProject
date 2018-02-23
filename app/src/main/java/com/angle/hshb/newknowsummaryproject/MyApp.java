package com.angle.hshb.newknowsummaryproject;

import android.app.Application;

import com.angle.hshb.newknowsummaryproject.utils.CrashHandler;

/**
 * Created by angle
 * 2018/2/23.
 */

public class MyApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
