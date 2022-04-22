package com.example.demo.slidingtimelinedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.demo.R;
import com.example.demo.slidemenudemo.ContentFragment;
import com.example.demo.slidemenudemo.Item;
import com.example.demo.slidemenudemo.MyAdapter;
import com.example.demo.timelinedemo.TimelineLayout;

import java.util.ArrayList;
import java.util.Random;

public class SlidingTimeLineActivity extends AppCompatActivity{
    private DrawerLayout drawer_layout;

    private FrameLayout frameLayout;

    private TimelineLayout mTimelineLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_time_line);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.ly_content);
        mTimelineLayout = (TimelineLayout) findViewById(R.id.timeline_layout);
        for (int i=0;i<10;i++){
            addItem();
        }


    }

    private void addItem() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_timeline, mTimelineLayout, false);
        Random random = new Random();
        ViewGroup.LayoutParams params = ((RelativeLayout) view.findViewById(R.id.rl_parent)).getLayoutParams();
        params.height = random.nextInt(201)+200;
        ( (RelativeLayout) view.findViewById(R.id.rl_parent)).setLayoutParams(params);
            ((TextView) view.findViewById(R.id.tv_action)).setText("1897年8月8日");
        mTimelineLayout.addView(view);
    }

    boolean isContentView(View child) {
        return ((DrawerLayout.LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
    }
}