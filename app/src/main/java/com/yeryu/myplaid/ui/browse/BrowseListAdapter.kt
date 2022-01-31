package com.yeryu.myplaid.ui.browse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.data.AccountProcessed
import com.yeryu.myplaid.ui.MainActivity
import com.yeryu.myplaid.ui.transaction.TransactionActivity

class BrowseListAdapter : RecyclerView.Adapter<BrowseListAdapter.BrowseItemViewHolder>() {

    private var data: ArrayList<AccountProcessed>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseItemViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.browse_list_item, parent, false)
        return BrowseItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrowseItemViewHolder, position: Int) {
        holder.bind(data!![position])
    }

    override fun getItemCount(): Int {
        return if (data == null) {
            0
        } else {
            data!!.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(data: ArrayList<AccountProcessed>?) {
        this.data = data
        notifyDataSetChanged()
    }

    class BrowseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.browse_item_name)
        private val typeTextView: TextView = itemView.findViewById(R.id.browse_item_type)
        private val countTextView: TextView = itemView.findViewById(R.id.browse_item_count)

        @SuppressLint("SetTextI18n")
        fun bind(accountProcessed: AccountProcessed) {
            val account = accountProcessed.account
            val transactions = accountProcessed.transactions

            nameTextView.text = account?.name
            typeTextView.text = account?.type?.replaceFirstChar { it.uppercase() }
            countTextView.text = transactions?.size.toString() + " txs"

            // Set on click listener on item.
            itemView.setOnClickListener { view: View ->
                val context = view.context
                val intent = Intent(view.context, TransactionActivity::class.java)

                val bundleData = Bundle().apply { putSerializable(TransactionActivity.transactionsDataKey, transactions) }
                intent.putExtras(bundleData)

                context.startActivity(intent)
            }
        }
    }
}