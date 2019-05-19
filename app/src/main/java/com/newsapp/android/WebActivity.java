package com.newsapp.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.newsapp.android.UserMode.DBOpenHelper;
import com.newsapp.android.exitsettings.BasicActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WebActivity extends BasicActivity {
    String user_phone;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = (WebView) findViewById(R.id.webView);
        //获取传递的路径
        String url = getIntent().getStringExtra("url");
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)){
            user_phone = inputText;
        }
        //加载路径
        webView.loadUrl(url);
        //显示JavaScript页面
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_webview);
        Toolbar ltoolBar = (Toolbar) findViewById(R.id.toolbar_webcomment);
        findViewById(R.id.toolbar_webcomment).bringToFront();
        setSupportActionBar(ltoolBar);
        toolbar.setLogo(R.mipmap.ic_launcher_foreground);
        toolbar.setTitle("淮工新闻");
        setSupportActionBar(toolbar);
        ltoolBar.inflateMenu(R.menu.tool_webbottom);
        ltoolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.news_share:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent,getTitle()));
                        break;
                    case R.id.news_collect:
                        Toast.makeText(WebActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Connection conn = null;
                                conn = (Connection) DBOpenHelper.getConn();
                                String uniquekey = getIntent().getStringExtra("uniquekey");
                                String sql = "insert into user_collect(user_phone,news_id) values(?,?) ";
                                int i = 0;
                                PreparedStatement pstmt;
                                try {
                                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                                    pstmt.setString(1,user_phone);
                                    pstmt.setString(2,uniquekey);
                                    i = pstmt.executeUpdate();
                                    pstmt.close();
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left);
        }
    }

    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_webview,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                WebActivity.this.finish();
                break;
            case R.id.news_search:
                Toast.makeText(this,"搜索新闻",Toast.LENGTH_SHORT).show();
                break;
            case R.id.news_setting:
                Toast.makeText(this,"夜间模式",Toast.LENGTH_SHORT).show();
                break;
            case R.id.news_feedback:
                break;
            default:
                break;
        }
        return true;
    }

}
