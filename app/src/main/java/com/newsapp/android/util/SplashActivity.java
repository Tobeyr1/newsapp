package com.newsapp.android.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.newsapp.android.MainActivity;
import com.newsapp.android.R;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        final LinearLayout tv_lin= (LinearLayout) findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin= (LinearLayout) findViewById(R.id.text_hide_lin);//所谓的布
        ImageView logo= (ImageView) findViewById(R.id.image);//图片
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash);
        logo.startAnimation(animation);//开始执行动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一个动画执行完后执行第二个动画就是那个字体显示那部分
                animation=AnimationUtils.loadAnimation(SplashActivity.this,R.anim.text_splash_position);
                tv_lin.startAnimation(animation);
                animation=AnimationUtils.loadAnimation(SplashActivity.this,R.anim.text_canvas);
                tv_hide_lin.startAnimation(animation);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}

