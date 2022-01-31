package com.yeryu.myplaid.api.data.common

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Payment(
    @SerializedName("by_order_of")
    var byOrderOf: String? = null,
    @SerializedName("payee")
    var payee: String? = null,
    @SerializedName("payer")
    var payer: String? = null,
    @SerializedName("payment_method")
    var paymentMethod: String? = null,
    @SerializedName("payment_processor")
    var paymentProcessor: String? = null,
    @SerializedName("ppd_id")
    var ppdId: String? = null,
    @SerializedName("reason")
    var reason: String? = null,
    @SerializedName("reference_number")
    var referenceNumber: String? = null
) : Serializable
