package com.example.demo.rippleanimationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.demo.R;

public class VideoActivity extends AppCompatActivity {
    private RippleView rippleView;
    private AppContext app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
        super.onCreate(savedInstanceState);
        app = (AppContext) getApplication();
        RipplePosition position = app.getPosition();

        rippleView = findViewById(R.id.ripple_view);

//        float x = getIntent().getFloatExtra("x",-1);
//        float y = getIntent().getFloatExtra("y",-1);
//        Log.e("TAG",x+","+y);
//        rippleView.startRippleAnimation(x,y);
        rippleView.post(() ->{
            rippleView.addOnRippleListener(state ->{
                if (state == RippleState.RIPPLE_START){
                    Toast.makeText(this, "animation start", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "animation end", Toast.LENGTH_SHORT).show();
                }
            });
            rippleView.startRippleAnimation(app.getPosition());
            });
    }

}