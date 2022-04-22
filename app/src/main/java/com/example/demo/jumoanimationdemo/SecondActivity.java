package com.example.demo.jumoanimationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.demo.R;
import com.example.demo.rippleanimationdemo.AppContext;
import com.example.demo.rippleanimationdemo.RipplePosition;
import com.example.demo.rippleanimationdemo.RippleState;
import com.example.demo.rippleanimationdemo.RippleView;

public class SecondActivity extends AppCompatActivity {
    VideoView videoView;
    RippleView rippleView;
//    String videoUrl = "android.resource://" + "com.example.demo" + "/" + R.raw.test;
    String videoUrl = "https://play-in-gdufs.oss-cn-beijing.aliyuncs.com/temp/新鲜出炉！一起来看广外宣传大片！ - 1.凤起岭南逐梦广外高清最终版1214(Av670664127,P1).mp4";
//    String videoUrl = "https://bitterlemonn.github.io/mov/bilibili_mov.mp4";
    private AppContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        videoView = findViewById(R.id.video_view);
        rippleView = findViewById(R.id.ripple_view);
        app = (AppContext) getApplication();
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.start();
        //使视频能够暂停、播放、进度条显示等控制
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        rippleView.post(() ->{
            rippleView.addOnRippleListener(state ->{
                if (state == RippleState.RIPPLE_START){
                    Toast.makeText(this, "animation start", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "animation end", Toast.LENGTH_SHORT).show();
                    rippleView.setVisibility(View.INVISIBLE);
                }
            });
            rippleView.startRippleAnimation(app.getPosition());
        });

    }
}