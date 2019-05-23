package com.newsapp.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.newsapp.android.TabAdapter.NewsFragment;
import com.newsapp.android.UserMode.DBOpenHelper;
import com.newsapp.android.UserMode.LoginActivity;
import com.newsapp.android.UserMode.LoginOutActivity;
import com.newsapp.android.UserMode.UserFavoriteActivity;
import com.newsapp.android.exitsettings.ActivityCollector;
import com.newsapp.android.exitsettings.BasicActivity;
import com.newsapp.android.gson.Data;
import com.viewpagerindicator.TabPageIndicator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends BasicActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;
    private TextView tvhuoqu;
    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView lvNews;
    private NewsAdapter adapter;
    private List<Data> dataList;
    private List<Fragment> mFragmentList;
    String phonenumber;
    String user_phone_number;
    private NavigationView navView;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvhuoqu = (TextView) findViewById(R.id.text_huoqu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        list = new ArrayList<>();
        list.add("头条");
        list.add("社会");
        list.add("国内");
        list.add("国际");
        list.add("娱乐");
        list.add("体育");
        list.add("军事");
        list.add("科技");
        list.add("财经");

      /*  lvNews = (ListView) findViewById(R.id.lvNews);
        dataList = new ArrayList<Data>();
        adapter = new NewsAdapter(this, dataList);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(this);
        sendRequestWithOKHttp();*/

      /*  mIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new TabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);*/



    }

    @Override
    protected void onStart() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)){
           phonenumber =inputText;
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this, "gallery", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this, "manage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_favorite:
                        if (phonenumber != null){
                            Intent userFavIntent = new Intent(MainActivity.this,UserFavoriteActivity.class);
                            userFavIntent.putExtra("test_user",phonenumber);
                            startActivity(userFavIntent);
                        } else {
                            Intent exitIntent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivityForResult(exitIntent,2);
                        }
                        break;
                    case R.id.nav_settings:
                        Intent exitIntent = new Intent(MainActivity.this,LoginOutActivity.class);
                        startActivity(exitIntent);
                        Toast.makeText(MainActivity.this,"需要做出登出功能，可扩展夜间模式，离线模式等,检查更新",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_view:
                        Toast.makeText(MainActivity.this, "manage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_exit:
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivityForResult(intent,1);
                        break;
                }
                return false;

            }
               /* return true;
            }*/
        });
        super.onStart();
        //viewPager+Fragment数据列表适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //得到当前页的标题，也就是设置当前页面显示的标题是tabLayout对应标题

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                NewsFragment newsFragment = new NewsFragment();

                //判断所选的标题，进行传值显示
                Bundle bundle = new Bundle();
                bundle.putString("phone",phonenumber);
                if (list.get(position).equals("头条")){
                    bundle.putString("name","top");
                }else if (list.get(position).equals("社会")){
                    bundle.putString("name","shehui");
                }else if (list.get(position).equals("国内")){
                    bundle.putString("name","guonei");
                }else if (list.get(position).equals("国际")){
                    bundle.putString("name","guoji");
                }else if (list.get(position).equals("娱乐")){
                    bundle.putString("name","yule");
                }else if (list.get(position).equals("体育")){
                    bundle.putString("name","tiyu");
                }else if (list.get(position).equals("军事")){
                    bundle.putString("name","junshi");
                }else if (list.get(position).equals("科技")){
                    bundle.putString("name","keji");
                }else if (list.get(position).equals("财经")){
                    bundle.putString("name","caijing");
                }else if (list.get(position).equals("时尚")){
                    bundle.putString("name","shishang");
                }
                newsFragment.setArguments(bundle);
                return newsFragment;
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        //TabLayout要与ViewPAger关联显示
        tabLayout.setupWithViewPager(viewPager);

    }
    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            System.out.println("是否读到文件内容"+in);
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
    public String getPhonenumber(){
        return phonenumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    tvhuoqu = (TextView) findViewById(R.id.text_huoqu);
                    tvhuoqu.setText(returnedData);
                    phonenumber = returnedData;
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("data_return");
                    tvhuoqu = (TextView) findViewById(R.id.text_huoqu);
                    tvhuoqu.setText(returnedData);
                    phonenumber = returnedData;
                }
                break;
            default:
        }
        switch (resultCode){
            case RESULT_OK:
                String returnedData = data.getStringExtra("data_return");
                System.out.println("fanhuizhi**********###"+returnedData);
                tvhuoqu = (TextView) findViewById(R.id.text_huoqu);
                tvhuoqu.setText(returnedData);
                phonenumber = returnedData;
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        }
        else{//退出程序
            this.finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.userFeedback:
                final EditText ed =new EditText(MainActivity.this);
                AlertDialog.Builder uDialog = new AlertDialog.Builder(MainActivity.this);
               uDialog.setTitle("用户反馈");
                uDialog.setView(ed);
                uDialog.setCancelable(false);
                uDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String input_text = ed.getText().toString();
                                Connection conn = null;
                                conn = (Connection) DBOpenHelper.getConn();
                                String sql = "insert into user_feedback(user_feed) values(?)";
                                int i = 0;
                                PreparedStatement pstmt;
                                try {
                                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                                    pstmt.setString(1,input_text);
                                    i = pstmt.executeUpdate();
                                    pstmt.close();
                                    conn.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });
                uDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                uDialog.show();
                break;
            case R.id.userExit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Warning");
                dialog.setMessage("是否推出程序？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                String inputText = load();
                if (!TextUtils.isEmpty(inputText)){
                    tvhuoqu = (TextView) findViewById(R.id.text_huoqu);
                    phonenumber = inputText;
                    tvhuoqu.setText(phonenumber);
                }
                break;
            default:
                break;
        }
        return true;
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Data data = dataList.get(position);
        Intent intent = new Intent(this, BrowseNewsActivity.class);
        intent.putExtra("content_url", data.getUrl());
        startActivity(intent);
    }*/

}
