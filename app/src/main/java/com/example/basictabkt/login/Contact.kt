package com.example.basictabkt.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Contact {

    @SerializedName("_id")
    @Expose
    private var _id: String? = null
    @SerializedName("phNum")
    @Expose
    private var phNum: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("image_url")
    @Expose
    private var image_url: String? = null
    @SerializedName("image_title")
    @Expose
    private var image_title: String? = null

    /* 중략... */
    fun get_id(): String? {
        return _id
    }
    fun set_id(_id: String) {
        this._id = _id
    }
    fun get_phNum(): String? {
        return phNum
    }
    fun set_phNum(id: String?) {
        this.phNum = phNum
    }
    fun get_name(): String? {
        return name
    }
    fun set_name(name: String) {
        this.name = name
    }

    fun get_url(): String? {
        return image_url
    }

    fun get_title(): String? {
        return image_title
    }
}