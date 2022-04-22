package com.example.demo.rippleanimationdemo;

import android.app.Application;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.rippleanimationdemo
 * Author：陈冰
 * Date：2022/4/3 14:40
 */
public class AppContext extends Application {
    private RipplePosition position;

    public RipplePosition getPosition() {
        return position;
    }

    public void setPosition(RipplePosition position) {
        this.position = position;
    }
}