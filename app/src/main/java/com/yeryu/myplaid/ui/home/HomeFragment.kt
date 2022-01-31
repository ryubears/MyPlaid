package com.yeryu.myplaid.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.data.AccountProcessed
import com.yeryu.myplaid.ui.MainActivity
import com.yeryu.myplaid.ui.browse.BrowseListAdapter
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    var yearCostTextView: TextView? = null
    var monthCostTextView: TextView? = null
    var weekCostTextView: TextView? = null
    var sp500CostTextView: TextView? = null
    var qqqCostTextView: TextView? = null
    var bitcoinCostTextView: TextView? = null
    var ethereumTextView: TextView? = null

    var homeContext: Context? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        homeContext = rootView.context

        yearCostTextView = rootView.findViewById(R.id.home_year_cost)
        monthCostTextView = rootView.findViewById(R.id.home_month_cost)
        weekCostTextView= rootView.findViewById(R.id.home_week_cost)
        sp500CostTextView = rootView.findViewById(R.id.sp500_cost)
        qqqCostTextView = rootView.findViewById(R.id.qqq_cost)
        bitcoinCostTextView = rootView.findViewById(R.id.bitcoin_cost)
        ethereumTextView = rootView.findViewById(R.id.ethereum_cost)

        if (arguments != null) {
            val data = arguments?.getSerializable(MainActivity.processedDataKey) as ArrayList<AccountProcessed>
            processData(data)
        }

        return rootView
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun processData(data: ArrayList<AccountProcessed>) {
        var yearCost = 0.0
        var monthCost = 0.0
        var weekCost = 0.0
        var sp500Cost = 0.0
        var bitcoinCost = 0.0

        for (account in data) {
            val transactions = account.transactions
            if (transactions != null && transactions.isNotEmpty()) {
                for (transactionProcessed in transactions) {
                    val transaction = transactionProcessed.transaction!!
                    val opportunityCost = transactionProcessed.opportunityCost!!

                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val calendar = GregorianCalendar()
                    calendar.time = format.parse(transaction.date!!)!!
                    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

                    yearCost += opportunityCost
                    if (dayOfYear >= 336) {
                        monthCost += opportunityCost
                    }
                    if (dayOfYear >= 359) {
                        weekCost += opportunityCost
                    }

                    val sp500OpportunityCost = transactionProcessed.sp500OpportunityCost!!
                    val bitcoinOpportunityCost = transactionProcessed.bitcoinOpportunityCost!!

                    sp500Cost += sp500OpportunityCost
                    bitcoinCost += bitcoinOpportunityCost
                }
            }
        }

        if (yearCost > 0) {
            yearCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
            yearCostTextView?.text = "-$${String.format("%.2f", yearCost)}"
        } else {
            if (yearCost < 0) yearCost *= -1
            yearCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
            yearCostTextView?.text = "$${String.format("%.2f", yearCost)}"
        }

        if (monthCost > 0) {
            monthCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
            monthCostTextView?.text = "-$${String.format("%.2f", monthCost)}"
        } else {
            if (monthCost < 0) monthCost *= -1
            monthCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
            monthCostTextView?.text = "$${String.format("%.2f", monthCost)}"
        }

        if (weekCost > 0) {
            weekCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
            weekCostTextView?.text = "-$${String.format("%.2f", weekCost)}"
        } else {
            if (weekCost < 0) weekCost *= -1
            weekCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
            weekCostTextView?.text = "$${String.format("%.2f", weekCost)}"
        }

        if (sp500Cost > 0) {
            sp500CostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
            sp500CostTextView?.text = "-$${String.format("%.2f", sp500Cost)}"
        } else {
            if (sp500Cost < 0) sp500Cost *= -1
            sp500CostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
            sp500CostTextView?.text = "$${String.format("%.2f", sp500Cost)}"
        }

        qqqCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
        qqqCostTextView?.text = "-$${String.format("%.2f", 3914.84)}"

        if (bitcoinCost > 0) {
            bitcoinCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.red))
            bitcoinCostTextView?.text = "-$${String.format("%.2f", bitcoinCost)}"
        } else {
            if (bitcoinCost < 0) bitcoinCost *= -1
            bitcoinCostTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
            bitcoinCostTextView?.text = "$${String.format("%.2f", bitcoinCost)}"
        }

        ethereumTextView?.setTextColor(ContextCompat.getColor(homeContext!!, R.color.green))
        ethereumTextView?.text = "$${String.format("%.2f", 521.47)}"
    }

}