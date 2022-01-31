package com.yeryu.myplaid.api.data.investments

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Security(
    @SerializedName("close_price")
    var closePrice: Double?  = null,
    @SerializedName("close_price_as_of")
    var closePriceAsOf: String?  = null,
    @SerializedName("cusip")
    var cusip: String?  = null,
    @SerializedName("institution_id")
    var institutionId: String?  = null,
    @SerializedName("institution_security_id")
    var institutionSecurityId: String?  = null,
    @SerializedName("is_cash_equivalent")
    var isCashEquivalent: Boolean? = null,
    @SerializedName("isin")
    var isin: String?  = null,
    @SerializedName("iso_currency_code")
    var isoCurrencyCode: String?  = null,
    @SerializedName("name")
    var name: String?  = null,
    @SerializedName("proxy_security_id")
    var proxySecurityId: String?  = null,
    @SerializedName("security_id")
    var securityId : String?  = null,
    @SerializedName("sedol")
    var sedol: String?  = null,
    @SerializedName("ticker_symbol")
    var tickerSymbol: String?  = null,
    @SerializedName("type")
    var type: String?  = null,
    @SerializedName("unofficial_currency_code")
    var unofficialCurrencyCode: String?  = null
) : Serializable
