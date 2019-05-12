package com.newsapp.android.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.newsapp.android.gson.News;
import com.newsapp.android.gson.Result;

public class Utillity {
   /* 解析和处理服务器返回的数据*/
    public static News pareJsonWithGson(final String requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText,News.class);
    }

}
