package com.example.demo.mvpdemo.bean;

import com.example.demo.mvpdemo.bean.Person;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvpdemo.model
 * Author：陈冰
 * Date：2022/4/17 11:06
 */
public class ResponseData<T> {


    private String msg;
    private Integer code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
