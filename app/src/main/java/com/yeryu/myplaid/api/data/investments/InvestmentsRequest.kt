package com.yeryu.myplaid.api.data.investments

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class InvestmentsRequest(
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("secret")
    var clientSecret: String,
    @SerializedName("access_token")
    var accessToken: String
) : Serializable
