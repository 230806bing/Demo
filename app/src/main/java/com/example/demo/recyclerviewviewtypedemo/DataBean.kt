package com.example.demo.recyclerviewviewtypedemo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DataBean() : Parcelable {
    var title: String? = null
    var content: String? = null
    var portrait: Int? = null
    var type: Int? = null

    constructor(content: String, portrait: Int, type: Int) : this() {
        this.content = content
        this.portrait = portrait
        this.type = type
    }

    constructor(title: String, portrait: Int, content: String,type: Int) : this() {
        this.title = title
        this.portrait = portrait
        this.content = content
        this.type = type
    }


}
