package com.yeryu.myplaid.api.data.transactions

import com.google.gson.annotations.SerializedName
import com.yeryu.myplaid.api.data.common.Account
import com.yeryu.myplaid.api.data.common.Item
import java.io.Serializable

data class TransactionsResponse(
    @SerializedName("accounts")
    var accounts: List<Account> = listOf(),
    @SerializedName("transactions")
    var transactions: List<Transaction> = listOf(),
    @SerializedName("item")
    var item: Item? = Item(),
    @SerializedName("total_transactions")
    var totalTransactions: Int? = null,
    @SerializedName("request_id")
    var requestId: String? = null
) : Serializable
