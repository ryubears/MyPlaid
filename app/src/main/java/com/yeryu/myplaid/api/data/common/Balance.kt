package com.yeryu.myplaid.api.data.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Balance(
    @SerializedName("available")
    var available: Double? = null,
    @SerializedName("current")
    var current: Double? = null,
    @SerializedName("iso_currency_code")
    var isoCurrencyCode: String? = null,
    @SerializedName("limit")
    var limit: String? = null,
    @SerializedName("unofficial_currency_code")
    var unofficialCurrencyCode: String? = null
) : Serializable
