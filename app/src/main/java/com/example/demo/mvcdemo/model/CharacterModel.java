package com.example.demo.mvcdemo.model;

/**
 * Description：
 * Param：
 * return：
 * PackageName：com.example.demo.mvcdemo
 * Author：陈冰
 * Date：2022/4/15 15:29
 */
public interface CharacterModel {
    void getCharacter(String token,String id,OnCharacterListener listener);
}
