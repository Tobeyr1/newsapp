package com.newsapp.android.util;

import android.app.Application;
import android.content.Context;

public class NewApplication extends Application {
    private static Context context;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 获取当前主线程id
        mainThreadId = android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }
}
