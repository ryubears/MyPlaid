package com.yeryu.myplaid.api.data.token

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LinkTokenResponse(
    @SerializedName("expiration")
    var expiration: String,
    @SerializedName("link_token")
    var linkToken: String,
    @SerializedName("request_id")
    var requestId: String
) : Serializable