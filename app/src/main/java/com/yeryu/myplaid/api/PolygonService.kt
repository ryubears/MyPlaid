package com.yeryu.myplaid.api

import com.yeryu.myplaid.api.data.investments.InvestmentsRequest
import com.yeryu.myplaid.api.data.investments.InvestmentsResponse
import com.yeryu.myplaid.api.data.price.PriceResponse
import com.yeryu.myplaid.api.data.token.AccessTokenRequest
import com.yeryu.myplaid.api.data.token.AccessTokenResponse
import com.yeryu.myplaid.api.data.token.LinkTokenRequest
import com.yeryu.myplaid.api.data.token.LinkTokenResponse
import com.yeryu.myplaid.api.data.transactions.TransactionsRequest
import com.yeryu.myplaid.api.data.transactions.TransactionsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface PolygonService {
    @GET("v2/aggs/ticker/{ticker}/range/1/day/{from}/{to}")
    @Headers("Content-Type: application/json")
    fun getPrice(
        @Path(value="ticker", encoded=true) ticker:String,
        @Path(value="from", encoded=true) fromDate: String,
        @Path(value="to", encoded=true) toDate: String,
        @Query("apiKey") apiKey: String,
        @Query("limit") limit: String = "365"
    ): Observable<PriceResponse>
}