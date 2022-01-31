package com.yeryu.myplaid.api.data.token

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LinkTokenRequest(
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("secret")
    var clientSecret: String,
    @SerializedName("client_name")
    var clientName: String = "MyPlaid",
    @SerializedName("country_codes")
    var countryCodes: List<String> = listOf("US"),
    @SerializedName("language")
    var language: String = "en",
    @SerializedName("user")
    var user: User = User("unique_user_id"),
    @SerializedName("products")
    var products: List<String> = listOf("auth", "transactions", "investments", "identity"),
) : Serializable {
    data class User(
        @SerializedName("client_user_id")
        var userId: String
    ) : Serializable
}
