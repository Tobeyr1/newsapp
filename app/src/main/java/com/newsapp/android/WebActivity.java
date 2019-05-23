package com.newsapp.android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.newsapp.android.UserMode.DBOpenHelper;
import com.newsapp.android.UserMode.LoginActivity;
import com.newsapp.android.exitsettings.BasicActivity;
import com.newsapp.android.gson.NewsBean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WebActivity extends BasicActivity {
    String user_phone;
    String user_phonenumber;
    private List<NewsBean.ResultBean.DataBean> list;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        user_phonenumber = getIntent().getStringExtra("usernumbbbb");
        System.out.println("Web初始化是否获取手机号"+user_phonenumber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_webview);
        Toolbar ltoolBar = (Toolbar) findViewById(R.id.toolbar_webcomment);
        findViewById(R.id.toolbar_webcomment).bringToFront();
        setSupportActionBar(ltoolBar);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)){
            user_phone = inputText;
            System.out.println("全局手机号"+user_phone);
        }
        toolbar.setLogo(R.mipmap.ic_launcher_foreground);
        toolbar.setTitle("淮工新闻");
        setSupportActionBar(toolbar);
        ltoolBar.inflateMenu(R.menu.tool_webbottom);
        ltoolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.news_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent,getTitle()));
                        break;
                    case R.id.news_collect:
                        System.out.println("Web页面是否获取手机号:"+user_phonenumber);
                        if (user_phonenumber != null){
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
                                        pstmt.setString(1,user_phonenumber);
                                        pstmt.setString(2,uniquekey);
                                        i = pstmt.executeUpdate();
                                        pstmt.close();
                                        conn.close();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            Intent exitIntent = new Intent(WebActivity.this,LoginActivity.class);
                            startActivityForResult(exitIntent,3);
                        }
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
        //获取传递的路径
        WebView webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        //加载路径
        webView.loadUrl(url);
        //显示JavaScript页面
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 3:
                if (resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    user_phonenumber =returnedData;
                    if (returnedData != null){
                    }else {
                        Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
        }
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("data_return",user_phonenumber);
        System.out.println("*!!!!!!!!!!!"+user_phonenumber);
        setResult(RESULT_OK,returnIntent);
        Bundle bundle = new Bundle();
        returnIntent.putExtras(bundle);
        finish();
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
                Intent returnIntent = new Intent();
                returnIntent.putExtra("data_return",user_phonenumber);
                System.out.println("&&&&&&&&&&");
                System.out.println("&&&&&&&&&&"+user_phonenumber);
                setResult(RESULT_OK,returnIntent);
                Bundle bundle = new Bundle();
                returnIntent.putExtras(bundle);
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
