package com.yeryu.myplaid.api.data.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item (
    @SerializedName("available_products")
    var availableProducts: List<String> = listOf(),
    @SerializedName("billed_products")
    var billedProducts: List<String> = listOf(),
    @SerializedName("consent_expiration_time")
    var consentExpirationTime: String? = null,
    @SerializedName("error")
    var error: String? = null,
    @SerializedName("institution_id")
    var institutionId: String? = null,
    @SerializedName("item_id")
    var itemId: String? = null,
    @SerializedName("update_type")
    var updateType: String? = null,
    @SerializedName("webhook")
    var webhook: String? = null
) : Serializable
