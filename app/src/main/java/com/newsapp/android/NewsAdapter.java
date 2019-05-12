package com.newsapp.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsapp.android.gson.Data;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends BaseAdapter  {
    //声明一个上下文的对象（后续的getView()方法当中，会用到LayoutInflater加载XML布局）
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private List<Data> dataList;
    private LayoutInflater mInflater;


    //构造方法
    public NewsAdapter(Context context, List<Data> dataList){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }


    //控制该Adapter包含列表项的个数
    @Override
    public int getCount() {
        return dataList.size();
    }

    //决定第position处的列表项内容
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //决定第position处的列表项ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    //决定第position处的列表项组件
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.news_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            viewHolder.tvAuth = (TextView) view.findViewById(R.id.tvAuth);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.tvTime);
            viewHolder.ivPic = (ImageView) view.findViewById(R.id.ivPic);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Data data = dataList.get(position);
        viewHolder.tvTitle.setText(data.getTitle());
        viewHolder.tvAuth.setText(data.getAuthorName());
        viewHolder.tvTime.setText(data.getDate());
        String pic_url = data.getThumbnail_pic_s();
        setPicBitmap(viewHolder.ivPic, pic_url);
        return view;
    }
    class ViewHolder {
        TextView tvTitle;
        TextView tvAuth;
        TextView tvTime;
        ImageView ivPic;
    }

    public static void setPicBitmap(final ImageView ivPic, final String pic_url){
        //设置图片需要访问网络，因此不能在主线程中设置
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    HttpURLConnection conn = (HttpURLConnection)new URL(pic_url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    ivPic.setImageBitmap(bitmap);
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
