package com.yeryu.myplaid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.plaid.link.OpenPlaidLink
import com.plaid.link.PlaidActivityResultContract
import com.plaid.link.linkTokenConfiguration
import com.plaid.link.result.LinkExit
import com.plaid.link.result.LinkSuccess
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.PlaidService
import com.yeryu.myplaid.api.ApiCredentials
import com.yeryu.myplaid.api.data.token.LinkTokenRequest
import com.yeryu.myplaid.api.data.token.LinkTokenResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// IMPORTANT: This is for demo purposes only.
// You would need to use separate backend for api interaction
// and refactor to follow better Android architecture.
@Suppress("USE_EXPERIMENTAL_ARGUMENT_IS_NOT_MARKER")
class LinkActivity : AppCompatActivity() {

    private val tag: String = "LinkActivity"
    private lateinit var plaidApi: PlaidService

    @OptIn(PlaidActivityResultContract::class)
    private val linkAccountToPlaid =
        registerForActivityResult(OpenPlaidLink()) {
            when (it) {
                is LinkSuccess -> {
                    it.publicToken
                    Log.d(tag, "Plaid Link successful")
                    val mainIntent = Intent(this, MainActivity::class.java)
                    mainIntent.putExtra(MainActivity.publicTokenKey, it.publicToken)
                    startActivity(mainIntent)
                    finish()
                }
                is LinkExit -> {
                    Log.d(tag, "Plaid Link exited")
                    finish()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        // Fetch link token and start Plaid Link flow.
        initApi()
        startPlaidLink()
    }

    private fun initApi() {
        // Build OkHttp client.
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)
        val client = builder.build()

        // Setup Retrofit instance.
        plaidApi = Retrofit.Builder()
            .baseUrl("https://sandbox.plaid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PlaidService::class.java)
    }

    private fun startPlaidLink() {
        val requestBody = LinkTokenRequest(ApiCredentials.plaidClientId, ApiCredentials.plaidClientSecret)
        plaidApi.getLinkToken(requestBody).enqueue(object: Callback<LinkTokenResponse> {
            override fun onResponse(call: Call<LinkTokenResponse>, response: Response<LinkTokenResponse>) {
                if (response.isSuccessful) {
                    // Open Plaid Link.
                    linkAccountToPlaid.launch(linkTokenConfiguration { token = response.body()?.linkToken })
                } else {
                    Log.e(tag, response.code().toString() + " " + response.message())
                }
            }
            override fun onFailure(call: Call<LinkTokenResponse>, t: Throwable) {
                Log.e(tag, t.toString())
            }
        })
    }
}