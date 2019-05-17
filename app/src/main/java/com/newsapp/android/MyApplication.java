package com.newsapp.android;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
        LitePal.initialize(context);
        MobSDK.init(this);
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
