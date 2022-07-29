package com.example.demo.eventbusdemo

import android.os.Message

/**
 *     author : chenbing
 *     e-mail : chenbing@corp.netease.com
 *     time   : 2022/07/28
 *     desc   :
 */
class MessageEvent constructor() {
    var message:String ?= null


    constructor(message: String) :this(){
        this.message = message
    }
}