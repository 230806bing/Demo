package com.example.demo.mvpdemo.presenter;

import com.example.demo.mvpdemo.model.LoginModel;
import com.example.demo.mvpdemo.model.listener.LoginListener;
import com.example.demo.mvpdemo.view.ILoginView;
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
public class LoginPresenter extends PresenterFather{

    public  LoginPresenter(ILoginView loginView) {
        this.mIModel = new LoginModel();
        this.mViewReference = new WeakReference<IView>(loginView);
    }

    public void login() {
        if (mIModel != null && mViewReference != null && mViewReference.get() != null) {
            ILoginView loginView = (ILoginView) mViewReference.get();
            String name = loginView.getUserName();
            String passWord = loginView.getPassword();
            loginView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((LoginModel) mIModel).login(name, passWord, new LoginListener() {
                @Override
                public void OnSuccess() {
                    if (mViewReference.get() != null) {
                        ((ILoginView) mViewReference.get()).onLoginSuccess();
                    }
                }

                @Override
                public void onFails() {
                    if (mViewReference.get() != null) {
                        if (mViewReference.get() != null) {
                            ((ILoginView) mViewReference.get()).onLoginFails();
                        }
                    }
                }
            });
        }

    }
}
