package com.example.basictabkt.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Contact {

    @SerializedName("_id")
    @Expose
    private var _id: String? = null

    //contacts
    @SerializedName("phNum")
    @Expose
    private var phNum: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null

    //gallery
    @SerializedName("image_url")
    @Expose
    private var image_url: String? = null
    @SerializedName("image_title")
    @Expose
    private var image_title: String? = null

    //tab3
    @SerializedName("year")
    @Expose
    private var year: String? = null
    @SerializedName("month")
    @Expose
    private var month: String? = null
    @SerializedName("day")
    @Expose
    private var day: String? = null
    @SerializedName("toDo")
    @Expose
    private var toDo: String? = null

    /* 중략... */
    fun get_id(): String? {
        return _id
    }
    fun set_id(_id: String) {
        this._id = _id
    }

    //
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

    //
    fun get_url(): String? {
        return image_url
    }
    fun get_title(): String? {
        return image_title
    }

    //
    fun get_year(): String? {
        return year
    }
    fun get_month(): String? {
        return month
    }
    fun get_day(): String? {
        return day
    }
    fun get_toDo(): String? {
        return toDo
    }

}