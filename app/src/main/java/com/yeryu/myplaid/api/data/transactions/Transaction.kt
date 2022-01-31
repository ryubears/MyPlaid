package com.yeryu.myplaid.api.data.transactions

import com.google.gson.annotations.SerializedName
import com.yeryu.myplaid.api.data.common.Location
import com.yeryu.myplaid.api.data.common.Payment
import java.io.Serializable

data class Transaction (
    @SerializedName("account_id")
    var accountId: String?= null,
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("iso_currency_code")
    var isoCurrencyCode: String? = null,
    @SerializedName("unofficial_currency_code")
    var unofficialCurrencyCode: String? = null,
    @SerializedName("category")
    var category: List<String> = listOf(),
    @SerializedName("category_id")
    var categoryId: String? = null,
    @SerializedName("check_number")
    var checkNumber: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("datetime")
    var datetime: String? = null,
    @SerializedName("authorized_date")
    var authorizedDate: String? = null,
    @SerializedName("authorized_datetime")
    var authorizedDatetime: String? = null,
    @SerializedName("location")
    var location: Location? = Location(),
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("merchant_name")
    var merchantName: String? = null,
    @SerializedName("payment_meta")
    var payment: Payment? = Payment(),
    @SerializedName("payment_channel")
    var paymentChannel: String? = null,
    @SerializedName("pending")
    var pending: Boolean? = null,
    @SerializedName("pending_transaction_id")
    var pendingTransactionId: String? = null,
    @SerializedName("account_owner")
    var accountOwner: String? = null,
    @SerializedName("transaction_id")
    var transactionId: String? = null,
    @SerializedName("transaction_code")
    var transactionCode: String? = null,
    @SerializedName("transaction_type")
    var transactionType: String? = null
) : Serializable
