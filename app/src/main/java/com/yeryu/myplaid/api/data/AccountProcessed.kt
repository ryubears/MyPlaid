package com.yeryu.myplaid.api.data

import com.yeryu.myplaid.api.data.common.Account
import com.yeryu.myplaid.api.data.transactions.TransactionProcessed
import java.io.Serializable

data class AccountProcessed(
    var account: Account? = null,
    var transactions: ArrayList<TransactionProcessed>? = null
) : Serializable
