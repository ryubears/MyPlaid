package com.yeryu.myplaid.ui.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.data.transactions.TransactionProcessed

class TransactionListAdapter : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    private var data: ArrayList<TransactionProcessed>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.transaction_list_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
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
    fun update(data: ArrayList<TransactionProcessed>?) {
        this.data = data
        notifyDataSetChanged()
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.transaction_item_name)
        private val dateTextView: TextView = itemView.findViewById(R.id.transaction_item_date)
        private val opportunityCostTextView: TextView = itemView.findViewById(R.id.transaction_item_opportunity_cost)
        private val amountTextView: TextView = itemView.findViewById(R.id.transaction_item_amount)

        @SuppressLint("SetTextI18n")
        fun bind(transactionProcessed: TransactionProcessed) {
            val transaction = transactionProcessed.transaction

            nameTextView.text = transaction?.name
            dateTextView.text = transaction?.date

            var opportunityCost = transactionProcessed.opportunityCost!!
            if (opportunityCost >= 0) {
                opportunityCostTextView.text = "-$${String.format("%.2f", opportunityCost)}"
                opportunityCostTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
            } else {
                opportunityCost *= -1
                opportunityCostTextView.text = "+$${String.format("%.2f", opportunityCost)}"
                opportunityCostTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))

            }

            amountTextView.text = "$${transaction?.amount}"
        }
    }

}