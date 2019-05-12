package com.newsapp.android.util;

import android.content.Context;
import android.view.View;

import com.newsapp.android.NewsAdapter;

public class Utils {
    public static Context getContext(){
        return NewApplication.getContext();
    }
    public static int getMainThreadId(){
        return NewApplication.getMainThreadId();
    }

    public static String getString(int id){
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }
    public static View inflate(int layoutId){
        return View.inflate(getContext(), layoutId, null);
    }
    public static boolean isRunOnUiThread(){
        return getMainThreadId() == android.os.Process.myTid();
    }
}
