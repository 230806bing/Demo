package com.example.demo.mvpdemo.bean;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvpdemo.model
 * Author：陈冰
 * Date：2022/4/17 11:05
 */
public class Person {

    private String path;
    private String phone;
    private String name;
    private Integer id;
    private Integer power;
    private String token;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Person{" +
                "path='" + path + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", power=" + power +
                ", token='" + token + '\'' +
                '}';
    }
}
