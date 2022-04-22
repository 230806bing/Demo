package com.example.demo.mvpdemo.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.demo.mvpdemo.bean.Person;
import com.example.demo.mvpdemo.bean.ResponseData;
import com.example.demo.mvpdemo.model.listener.LoginListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvpdemo.model.listener
 * Author：陈冰
 * Date：2022/4/17 10:30
 */
public class LoginModel implements IModel{
    private String mUserName = "12345";
    private String mPassword = "123";

    public void login(String userName, String password, LoginListener loginListener){
        if (loginListener == null){
            return;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("phone",userName);
        formBody.add("verification",password);
        Request request = new Request.Builder()
                .url("http://120.76.194.85:12800/user/login")
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                loginListener.onFails();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseData = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<ResponseData<Person>>() {
                }.getType();
                ResponseData<Person> responseBody = gson.fromJson(responseData,type);
                if (responseBody.getCode() == 200){
                    Log.e("tag", responseBody.getData().getName());
                    loginListener.OnSuccess();
                }else if(responseBody.getCode() == 400){
                    loginListener.onFails();
                    Log.e("TAG", responseBody.getMsg());
                }
            }
        });
//        if (mUserName.equals(userName) && mPassword.equals(password)){
//            loginListener.OnSuccess();
//        }else {
//            loginListener.onFails();
//        }
    }
}
