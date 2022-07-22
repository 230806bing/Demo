package com.example.demo.pushdemo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/19
 *     desc   :
 */
@Parcelize
class PushEntity() : Parcelable {
    var time: String ?=null
    var portrait: String ?=null
    var certification:String ?=null
    var certificationLocal:Int ?=null
    var portraitLocal: Int ?=null
    var title: String ?=null
    var content: String ?=null
    var pic: String ?=null
    var picLocal: Int ?=null
    var type:Int ?=null
    var isCertification:Boolean ?=null

    constructor(time:String,portrait:Int,certification:Int?,title:String,content:String,pic:Int?,isCertification:Boolean,type:Int):this(){
        this.time = time
        this.portraitLocal = portrait
        this.certificationLocal = certification
        this.title = title
        this.content = content
        this.picLocal =pic
        this.isCertification = isCertification
        this.type = type

    }





}