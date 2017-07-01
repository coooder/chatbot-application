package com.example.jiehui.chatrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingActivity_Robot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__robot);
        getSupportActionBar().setTitle(R.string.setting_robot);
    }
}
