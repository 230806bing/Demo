package com.example.demo.timelinedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demo.R;

import java.util.Random;

public class TimeLineActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addItemButton;
    private Button subItemButton;
    private Button addMarginButton;
    private Button subMarginButton;

    private TextView mCurrentMargin;

    private TimelineLayout mTimelineLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        initView();
    }

    private void initView() {
        addItemButton = (Button) findViewById(R.id.add_item);
        subItemButton = (Button) findViewById(R.id.sub_item);
        addMarginButton= (Button) findViewById(R.id.add_margin);
        subMarginButton= (Button) findViewById(R.id.sub_margin);
        mCurrentMargin= (TextView) findViewById(R.id.current_margin);
        mTimelineLayout = (TimelineLayout) findViewById(R.id.timeline_layout);

        addItemButton.setOnClickListener(this);
        subItemButton.setOnClickListener(this);
        addMarginButton.setOnClickListener(this);
        subMarginButton.setOnClickListener(this);
    }

    private int index = 0;
    private void addItem() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_timeline, mTimelineLayout, false);
        Random random = new Random();
        ViewGroup.LayoutParams params = ((RelativeLayout) view.findViewById(R.id.rl_parent)).getLayoutParams();
        params.height = random.nextInt(201)+200;
        ( (RelativeLayout) view.findViewById(R.id.rl_parent)).setLayoutParams(params);
        ((TextView) view.findViewById(R.id.tv_action)).setText("1897年8月8日" + index);
        mTimelineLayout.addView(view);
        index++;
    }

    private void subItem() {
        if (mTimelineLayout.getChildCount() > 0) {
            mTimelineLayout.removeViews(mTimelineLayout.getChildCount() - 1, 1);
            index--;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_item:
                addItem();
                break;
            case R.id.sub_item:
                subItem();
                break;
//            case R.id.add_margin:
//                int currentMargin = UIHelper.pxToDip(this, mTimelineLayout.getLineMarginLeft());
//                mTimelineLayout.setLineMarginLeft(UIHelper.dipToPx(this, ++currentMargin));
//                mCurrentMargin.setText("current line margin left is " + currentMargin + "dp");
//                break;
//            case R.id.sub_margin:
//                currentMargin = UIHelper.pxToDip(this, mTimelineLayout.getLineMarginLeft());
//                mTimelineLayout.setLineMarginLeft(UIHelper.dipToPx(this, --currentMargin));
//                mCurrentMargin.setText("current line margin left is " + currentMargin + "dp");
//                break;
            default:
                break;
        }
    }

}