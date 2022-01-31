package com.yeryu.myplaid.api.data.price

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(
    @SerializedName("v")
    var v: Double? = null,
    @SerializedName("vw")
    var vw: Double? = null,
    @SerializedName("o")
    var o: Double? = null,
    @SerializedName("c")
    var c: Double? = null,
    @SerializedName("h")
    var h: Double? = null,
    @SerializedName("l")
    var l: Double? = null,
    @SerializedName("t")
    var t: Long? = null,
    @SerializedName("n")
    var n: Long? = null
) : Serializable
