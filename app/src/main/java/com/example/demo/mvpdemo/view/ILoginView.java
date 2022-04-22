package com.example.demo.mvpdemo.view;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvpdemo.view
 * Author：陈冰
 * Date：2022/4/17 10:28
 */
public interface ILoginView extends IView{
    String getUserName();
    String getPassword();
    void onLoginSuccess();
    void onLoginFails();
}
