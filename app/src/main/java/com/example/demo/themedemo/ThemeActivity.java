package com.example.demo.themedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;

public class ThemeActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        textView = findViewById(R.id.modeTv);
        imageView2 = findViewById(R.id.imageView2);
        initMode();
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (AppCompatDelegate.getDefaultNightMode()){
                    case AppCompatDelegate.MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);  //切换为夜间模式
                        break;
                    case AppCompatDelegate.MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);  //切换为夜间模式
                        break;
                    default:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);  //切换为夜间模式
                        break;
                }
                recreate();
            }
        });
    }

    private void initMode() {
        switch (AppCompatDelegate.getDefaultNightMode()){
            case AppCompatDelegate.MODE_NIGHT_NO:
                textView.setText("日间模式");
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                textView.setText("夜间模式");
                break;
            default:
                textView.setText("日间模式");
                break;
        }
    }

}