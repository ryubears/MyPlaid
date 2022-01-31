package com.yeryu.myplaid.api.data.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Account(
    @SerializedName("account_id")
    var accountId: String? = null,
    @SerializedName("balances")
    var balances: Balance? = Balance(),
    @SerializedName("mask")
    var mask: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("official_name")
    var officialName: String? = null,
    @SerializedName("subtype")
    var subtype: String? = null,
    @SerializedName("type")
    var type: String? = null
) : Serializable
