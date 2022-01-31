package com.yeryu.myplaid.api.data.token

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessTokenResponse(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("item_id")
    var itemId: String,
    @SerializedName("request_id")
    var requestId: String
) : Serializable
