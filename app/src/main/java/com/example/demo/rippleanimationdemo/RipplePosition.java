package com.example.demo.rippleanimationdemo;

import com.amap.api.maps.model.LatLng;

import java.io.Serializable;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.rippleanimationdemo
 * Author：陈冰
 * Date：2022/4/2 13:55
 */
public class RipplePosition {
    private float startX,startY;

    public RipplePosition(float startX, float startY) {
        this.startX = startX;
        this.startY = startY;
    }


    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

}
