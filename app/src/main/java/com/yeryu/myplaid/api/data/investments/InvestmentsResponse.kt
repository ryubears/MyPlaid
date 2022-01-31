package com.yeryu.myplaid.api.data.investments

import com.google.gson.annotations.SerializedName
import com.yeryu.myplaid.api.data.common.Account
import com.yeryu.myplaid.api.data.common.Item
import java.io.Serializable

data class InvestmentsResponse(
    @SerializedName("accounts")
    var accounts: List<Account> = listOf(),
    @SerializedName("holdings")
    var holdings: List<Holding> = listOf(),
    @SerializedName("item")
    var item: Item? = Item(),
    @SerializedName("request_id")
    var requestId: String? = null,
    @SerializedName("securities")
    var securities: List<Security> = listOf()
) : Serializable
