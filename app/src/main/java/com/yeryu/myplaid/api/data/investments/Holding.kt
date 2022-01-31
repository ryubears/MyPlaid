package com.yeryu.myplaid.api.data.investments

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Holding(
    @SerializedName("account_id")
    var accountId: String? = null,
    @SerializedName("cost_basis")
    var costBasis: Double?    = null,
    @SerializedName("institution_price")
    var institutionPrice: Double?    = null,
    @SerializedName("institution_price_as_of")
    var institutionPriceAsOf: String? = null,
    @SerializedName("institution_value")
    var institutionValue: Double? = null,
    @SerializedName("iso_currency_code")
    var isoCurrencyCode: String? = null,
    @SerializedName("quantity")
    var quantity: Double? = null,
    @SerializedName("security_id")
    var securityId: String? = null,
    @SerializedName("unofficial_currency_code")
    var unofficialCurrencyCode: String? = null
) : Serializable
