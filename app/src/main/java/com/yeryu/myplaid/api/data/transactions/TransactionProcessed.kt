package com.yeryu.myplaid.api.data.transactions

import java.io.Serializable

data class TransactionProcessed(
    var transaction: Transaction? = null,
    var opportunityCost: Double? = null,
    val sp500OpportunityCost: Double? = null,
    val bitcoinOpportunityCost: Double? = null
) : Serializable