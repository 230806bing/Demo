package com.example.demo.rippleanimationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.demo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RippleActivity extends AppCompatActivity{
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        AppContext app = (AppContext) getApplication();
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, VideoActivity.class);
            float startX = v.getX() + v.getWidth() / 2;
            float startY = v.getY() + v.getHeight() / 2;
            RipplePosition position = new RipplePosition(startX, startY);
            app.setPosition(position);
            startActivity(intent);
            // 取消系统默认的 Activity 跳转动画
            overridePendingTransition(0, 0);
        });
    }

}