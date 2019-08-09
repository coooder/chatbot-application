package com.example.jiehui.chatrobot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutThis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_this);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.About);
    }
}
