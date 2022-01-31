package com.yeryu.myplaid.api

import com.yeryu.myplaid.api.data.investments.InvestmentsRequest
import com.yeryu.myplaid.api.data.investments.InvestmentsResponse
import com.yeryu.myplaid.api.data.token.AccessTokenRequest
import com.yeryu.myplaid.api.data.token.AccessTokenResponse
import com.yeryu.myplaid.api.data.token.LinkTokenRequest
import com.yeryu.myplaid.api.data.token.LinkTokenResponse
import com.yeryu.myplaid.api.data.transactions.TransactionsRequest
import com.yeryu.myplaid.api.data.transactions.TransactionsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PlaidService {
    @POST("link/token/create")
    @Headers("Content-Type: application/json")
    fun getLinkToken(@Body requestBody: LinkTokenRequest): Call<LinkTokenResponse>

    @POST("item/public_token/exchange")
    @Headers("Content-Type: application/json")
    fun getAccessToken(@Body requestBody: AccessTokenRequest): Observable<AccessTokenResponse>

    @POST("/transactions/get")
    @Headers("Content-Type: application/json")
    fun getTransactions(@Body requestBody: TransactionsRequest): Observable<TransactionsResponse>

    @POST("/investments/holdings/get")
    @Headers("Content-Type: application/json")
    fun getInvestments(@Body requestBody: InvestmentsRequest): Observable<InvestmentsResponse>
}