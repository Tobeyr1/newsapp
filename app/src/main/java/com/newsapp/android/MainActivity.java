package com.newsapp.android;

import android.content.Context;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newsapp.android.TabAdapter.NewsFragment;
import com.newsapp.android.TitleUtils.ChannelItem;
import com.newsapp.android.ViewPageTitle.TabAdapter;
import com.newsapp.android.exitsettings.ActivityCollector;
import com.newsapp.android.exitsettings.BasicActivity;
import com.newsapp.android.gson.Data;
import com.newsapp.android.gson.News;
import com.viewpagerindicator.TabPageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.newsapp.android.ViewPageTitle.TabAdapter.TITLES;

public class MainActivity extends BasicActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list;

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView lvNews;
    private NewsAdapter adapter;
    private List<Data> dataList;
    private List<Fragment> mFragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
       NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
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
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this, "manage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(MainActivity.this, "manage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_view:
                        Toast.makeText(MainActivity.this, "manage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_exit:
                        Toast.makeText(MainActivity.this, "切换账号，要做改动的地方", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;

            }
               /* return true;
            }*/
        });


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
        list.add("财经");

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


      //下拉刷新
       /* swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
       swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
                refreshNews();
            }
       });*/


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.userFeedback:

                Toast.makeText(this,"ni click 用户设置",Toast.LENGTH_SHORT).show();
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
