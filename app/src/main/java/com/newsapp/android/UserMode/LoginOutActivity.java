package com.newsapp.android.UserMode;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.newsapp.android.R;

public class LoginOutActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_out);
        btn = (Button) findViewById(R.id.btn_loginout);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginOutActivity.this);
        dialog.setMessage("退出当前帐号，将不能发表跟帖，同步收藏和关注内容。");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*退出当前用户*/
                Toast.makeText(LoginOutActivity.this,"退出用户帐号操作需要实现",Toast.LENGTH_LONG).show();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
}
