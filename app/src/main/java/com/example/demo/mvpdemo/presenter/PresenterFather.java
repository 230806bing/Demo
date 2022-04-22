package com.example.demo.mvpdemo.presenter;

import com.example.demo.mvpdemo.model.IModel;
import com.example.demo.mvpdemo.view.IView;

import java.lang.ref.WeakReference;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvpdemo.presenter
 * Author：陈冰
 * Date：2022/4/17 10:28
 */
public class PresenterFather {
    protected IModel mIModel;

    protected WeakReference<IView> mViewReference;
}
