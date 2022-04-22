package com.example.demo.mvcdemo.model;

import android.view.View;

import com.example.demo.mvcdemo.bean.DataResponse;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvcdemo
 * Author：陈冰
 * Date：2022/4/16 9:58
 */
public interface OnCharacterListener extends View.OnClickListener {
    void onError();
    void onSuccess(DataResponse dataBean);
}
