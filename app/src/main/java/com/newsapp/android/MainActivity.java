package com.newsapp.android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.newsapp.android.exitsettings.BasicActivity;

import org.litepal.LitePal;

public class MainActivity extends BasicActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button)findViewById(R.id.button_pal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*LitePal.getDatabase();*/
                Toast.makeText(MainActivity.this,"good job",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
