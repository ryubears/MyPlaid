package com.yeryu.myplaid.api.data.transactions

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransactionsRequest(
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("secret")
    var clientSecret: String,
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("start_date")
    var startDate: String = "2021-01-01",
    @SerializedName("end_date")
    var endDate: String = "2021-12-31"
) : Serializable
