package com.yeryu.myplaid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.ApiCredentials
import com.yeryu.myplaid.api.PlaidService
import com.yeryu.myplaid.api.PolygonService
import com.yeryu.myplaid.api.data.AccountProcessed
import com.yeryu.myplaid.api.data.investments.InvestmentsRequest
import com.yeryu.myplaid.api.data.investments.InvestmentsResponse
import com.yeryu.myplaid.api.data.price.PriceResponse
import com.yeryu.myplaid.api.data.token.AccessTokenRequest
import com.yeryu.myplaid.api.data.transactions.TransactionProcessed
import com.yeryu.myplaid.api.data.transactions.TransactionsRequest
import com.yeryu.myplaid.api.data.transactions.TransactionsResponse
import com.yeryu.myplaid.ui.browse.BrowseFragment
import com.yeryu.myplaid.ui.home.HomeFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// IMPORTANT: This is for demo purposes only.
// You would need to use separate backend for api interaction
// and refactor to follow better Android architecture.
class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val publicTokenKey = "PublicToken"
        @JvmStatic
        val processedDataKey = "ProcessedDataKey"
    }

    private var fragment: Fragment? = null
    private var fragmentTitle: String = "Home"
    private val browseFragment: Fragment = BrowseFragment()
    private val homeFragment: Fragment = HomeFragment()

    private lateinit var progressBar: ProgressBar

    private lateinit var plaidApi: PlaidService
    private lateinit var polygonApi: PolygonService
    private var bundleData: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.main_progress_bar)

        // Setup BottomNavigationBar.
        loadFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bottom_nav_bar)
        bottomNavigationView.menu.findItem(R.id.action_home).isChecked = true
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            fragmentTitle = item.title.toString()
            fragment = homeFragment
            if (getString(R.string.home_navigation).contentEquals(fragmentTitle)) {
                fragment = homeFragment
            } else if (getString(R.string.browse_navigation).contentEquals(fragmentTitle)) {
                fragment = browseFragment
            }
            loadFragment(fragment)
        }

        // Extract public token and fetch data.
        val publicToken = intent.getStringExtra(publicTokenKey)
        publicToken?.let {
            initApi()
            fetchData(publicToken)
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            if (bundleData != null) {
                fragment.arguments = bundleData
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .commitNow()
            return true
        }
        return false
    }

    private fun initApi() {
        // Build OkHttp client.
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(loggingInterceptor)
        val client = builder.build()

        // Setup Plaid API instance.
        plaidApi = Retrofit.Builder()
            .baseUrl("https://sandbox.plaid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(PlaidService::class.java)

        // Setup Plaid API instance.
        polygonApi = Retrofit.Builder()
            .baseUrl("https://api.polygon.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(PolygonService::class.java)
    }

    @SuppressLint("CheckResult")
    private fun fetchData(publicToken: String) {
        // Fetch access token.
        val accessTokenRequest = AccessTokenRequest(ApiCredentials.plaidClientId, ApiCredentials.plaidClientSecret, publicToken)
        plaidApi.getAccessToken(accessTokenRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                val accessToken = response.accessToken

                // Fetch transactions and investments data.
                val transactionsRequest = TransactionsRequest(ApiCredentials.plaidClientId, ApiCredentials.plaidClientSecret, accessToken)
                val transactionsObservable = plaidApi.getTransactions(transactionsRequest)
                val investmentsRequest = InvestmentsRequest(ApiCredentials.plaidClientId, ApiCredentials.plaidClientSecret, accessToken)
                val investmentsObservable = plaidApi.getInvestments(investmentsRequest)

                val plaidObservable = Observable.zip(transactionsObservable, investmentsObservable)
                { transactionsResponse, investmentsResponse -> Pair(transactionsResponse, investmentsResponse) }

                // Get S&P 500 and Bitcoin price.
                val sp500PriceObservable = polygonApi.getPrice("SPY", "2021-01-01", "2021-12-31", ApiCredentials.polygonApiKey)
                val bitcoinPriceObservable = polygonApi.getPrice("X:BTCUSD", "2021-01-01", "2021-12-31", ApiCredentials.polygonApiKey)

                val polygonObservable = Observable.zip(sp500PriceObservable, bitcoinPriceObservable)
                { sp500Response, bitcoinResponse -> Pair(sp500Response, bitcoinResponse) }

                Observable.zip(plaidObservable, polygonObservable)
                { plaidResponse, polygonResponse ->
                    Pair(plaidResponse, polygonResponse)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { dataResponse -> processData(dataResponse) }
            }
    }

    @SuppressLint("SimpleDateFormat")
    private fun processData(dataResponse: Pair<Pair<TransactionsResponse, InvestmentsResponse>, Pair<PriceResponse, PriceResponse>>) {
        val plaidResponse = dataResponse.first
        val polygonResponse = dataResponse.second

        val transactionsResponse = plaidResponse.first
        val investmentsResponse = plaidResponse.second

        val sp500Response = polygonResponse.first
        val bitcoinResponse = polygonResponse.second

        // Calculate investment multiplier.
        var initialCost = 0.0
        var currentValue = 0.0
        for (holding in investmentsResponse.holdings) {
            val costBasis = holding.costBasis
            val price = holding.institutionPrice
            val value = holding.institutionValue
            val quantity = holding.quantity
            if (costBasis != null && price != null && value != null && quantity != null) {
                if (price / costBasis <= 100) {
                    initialCost += costBasis * quantity
                    currentValue += value
                }
            }
        }
        val multiplier = currentValue / initialCost - 1

        val sp500Size = sp500Response.results.size
        val bitcoinSize = bitcoinResponse.results.size
        val sp500FinalPrice = sp500Response.results[sp500Size - 1].c!!
        val bitcoinFinalPrice = bitcoinResponse.results[bitcoinSize - 1].c!!

        // Add account data.
        val processedMap = hashMapOf<String, AccountProcessed>()
        for (account in investmentsResponse.accounts) {
            val accountId = account.accountId
            if (accountId != null) {
                processedMap[accountId] = AccountProcessed(account, arrayListOf())
            }
        }

        // Add transaction data.
        for (transaction in transactionsResponse.transactions) {
            val accountId = transaction.accountId
            val amount = transaction.amount
            val dateStr = transaction.date
            if (accountId != null && amount != null && dateStr != null && amount > 0) {
                val format = SimpleDateFormat("yyyy-MM-dd")
                val date = format.parse(dateStr)
                if (date != null) {
                    val calendar = GregorianCalendar()
                    calendar.time = date
                    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

                    val sp500Price = sp500Response.results[sp500Size * dayOfYear / 365].c!!
                    val bitcoinPrice = bitcoinResponse.results[bitcoinSize * dayOfYear / 365].c!!

                    val opportunityCost = amount * multiplier * (366 - dayOfYear) / 365
                    val sp500OpportunityCost = amount * (sp500FinalPrice / sp500Price - 1)
                    val bitcoinOpportunityCost = amount * (bitcoinFinalPrice / bitcoinPrice - 1)

                    val transactionProcessed = TransactionProcessed(transaction, opportunityCost, sp500OpportunityCost, bitcoinOpportunityCost)
                    processedMap[accountId]?.transactions?.add(transactionProcessed)
                }
            }
        }

        // Convert to list.
        val processedData: ArrayList<AccountProcessed> = arrayListOf()
        for ((_, value) in processedMap) {
            processedData.add(value)
        }
        processedData.sortWith(compareByDescending { it.transactions?.size })

        updateData(processedData)
    }

    private fun updateData(processedData: ArrayList<AccountProcessed>) {
        progressBar.visibility = View.GONE

        // Reload fragment.
        bundleData = Bundle().apply { putSerializable(processedDataKey, processedData) }

        var newFragment: Fragment? = null
        if (getString(R.string.home_navigation).contentEquals(fragmentTitle)) {
            newFragment = HomeFragment()
        } else if (getString(R.string.browse_navigation).contentEquals(fragmentTitle)) {
            newFragment = BrowseFragment()
        }

        newFragment?.let {
            it.arguments = bundleData
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, it)
                .commitNow()
        }
    }
}