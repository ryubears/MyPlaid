package com.yeryu.myplaid.api.data.token

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessTokenRequest(
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("secret")
    var clientSecret: String,
    @SerializedName("public_token")
    var publicToken: String
) : Serializable
