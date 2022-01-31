package com.yeryu.myplaid.api.data.price

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PriceResponse(
    @SerializedName("ticker")
    var ticker: String? = null,
    @SerializedName("queryCount")
    var queryCount: Long? = null,
    @SerializedName("resultsCount")
    var resultsCount: Long? = null,
    @SerializedName("adjusted")
    var adjusted: Boolean? = null,
    @SerializedName("results")
    var results: ArrayList<Price> = arrayListOf(),
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("request_id")
    var requestId: String? = null,
    @SerializedName("count")
    var count: Long? = null
) : Serializable
