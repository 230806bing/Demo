package com.example.demo.mvcdemo.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.demo.mvcdemo.bean.DataResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvcdemo
 * Author：陈冰
 * Date：2022/4/15 15:31
 */
public class CharacterModelImpl implements CharacterModel{
    int code;
    String msg;
    DataResponse.DataBean dataBean = new DataResponse.DataBean();
    @Override
    public void getCharacter(String token, String id,OnCharacterListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://120.76.194.85:12800/browse/character/one?id="+id)
                .addHeader("token",token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("失败", "请求数据失败！" );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseData =response.body().string();
                Gson gson = new Gson();
                DataResponse dataResponse = gson.fromJson(responseData,DataResponse.class);
                code = dataResponse.getCode();
                msg = dataResponse.getMsg();
                dataBean = dataResponse.getData();
                if (dataBean!=null){
                    listener.onSuccess(dataResponse);
                }else {
                    listener.onError();
                }

            }
        });
    }
}
