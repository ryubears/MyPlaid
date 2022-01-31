package com.yeryu.myplaid.api.data.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("region")
    var region: String? = null,
    @SerializedName("postal_code")
    var postalCode: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lon")
    var lon: Double? = null,
    @SerializedName("store_number")
    var storeNumber: String? = null
) : Serializable
