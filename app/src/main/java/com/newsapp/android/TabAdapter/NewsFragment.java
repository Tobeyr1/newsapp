package com.newsapp.android.TabAdapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.gson.Gson;
import com.newsapp.android.R;
import com.newsapp.android.WebActivity;
import com.newsapp.android.gson.NewsBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class NewsFragment extends Fragment {
    private List<NewsBean.ResultBean.DataBean> list;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item,container,false);
        listView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String data = bundle.getString("name","top");

        //异步加载数据
        getDataFromNet(data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击条目的路径，传值显示webview页面
                String url = list.get(position).getUrl();
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }
    private void getDataFromNet(final String data){
       @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,String> task = new AsyncTask<Void, Void, String>() {
           @Override
           protected String doInBackground(Void... params) {
               String path = "http://v.juhe.cn/toutiao/index?type="+data+"&key=547ee75ef186fc55a8f015e38dcfdb9a";
               URL url = null;
               try {
                   url = new URL(path);
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setReadTimeout(5000);
                   connection.setConnectTimeout(5000);

                   int responseCode = connection.getResponseCode();
                   if (responseCode == 200){
                       InputStream inputStream = connection.getInputStream();
                       String json = streamToString(inputStream,"utf-8");
                       return json;
                   } else {
                       System.out.println(responseCode);
                       return "已达到今日访问次数上限";
                   }

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (ProtocolException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               return "";
           }
           protected void onPostExecute(String result){
               NewsBean newsBean = new Gson().fromJson(result,NewsBean.class);
               list = newsBean.getResult().getData();
               MyTabAdapter adapter = new MyTabAdapter(getActivity(),list);
               listView.setAdapter(adapter);
           }
       };
          task.execute();
    }

    private String streamToString(InputStream inputStream, String charset){
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
