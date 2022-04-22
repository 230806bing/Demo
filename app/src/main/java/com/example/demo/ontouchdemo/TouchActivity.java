package com.example.demo.ontouchdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.mapdemo.MainActivity;

public class TouchActivity extends AppCompatActivity implements View.OnTouchListener {
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        relativeLayout = findViewById(R.id.rl_touch);

        relativeLayout.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            /**
             * 点击的开始位置
             */
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(TouchActivity.this, "开始位置：(" + event.getX() + "," + event.getY(), Toast.LENGTH_SHORT).show();
                break;
            /**
             * 触屏实时位置
             */
            case MotionEvent.ACTION_MOVE:
                Toast.makeText(TouchActivity.this, "实时位置：(" + event.getX() + "," + event.getY(), Toast.LENGTH_SHORT).show();
                break;
            /**
             * 离开屏幕的位置
             */
            case MotionEvent.ACTION_UP:
                Toast.makeText(TouchActivity.this, "结束位置：(" + event.getX() + "," + event.getY(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}