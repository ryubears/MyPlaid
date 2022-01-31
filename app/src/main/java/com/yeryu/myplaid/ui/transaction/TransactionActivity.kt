package com.yeryu.myplaid.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.data.transactions.TransactionProcessed

class TransactionActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        val transactionsDataKey = "TransactionsDataKey"
    }

    private var searchEditText: EditText? = null
    private var emptyView: TextView? = null
    private var recyclerView: RecyclerView? = null

    private val adapter = TransactionListAdapter()
    private var data: ArrayList<TransactionProcessed>? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        setupSearchView()

        // Setup recycler view.
        emptyView = findViewById(R.id.transaction_empty_view)
        recyclerView = findViewById(R.id.transaction_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = adapter

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.default_list_divider)!!)
        recyclerView?.addItemDecoration(divider)

        val bundle = intent.extras
        if (bundle != null) {
            data = bundle.getSerializable(transactionsDataKey) as ArrayList<TransactionProcessed>
            if (data!!.isEmpty()) {
                emptyView?.visibility = View.VISIBLE
            } else {
                emptyView?.visibility = View.GONE
                adapter.update(data)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearchView() {
        searchEditText = findViewById(R.id.transaction_search_edit_text)
        val searchCloseButton = findViewById<ImageButton>(R.id.transaction_close_icon)
        val touchInterceptor = findViewById<FrameLayout>(R.id.transaction_touch_interceptor)

        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(searchText: CharSequence, start: Int, before: Int, after: Int) {
                val searchTextLowerCase = searchText.toString().lowercase()

                if (searchText.isEmpty()) {
                    searchCloseButton.visibility = View.GONE
                } else {
                    searchCloseButton.visibility = View.VISIBLE
                }

                data?.let {
                    val filteredData: ArrayList<TransactionProcessed> = arrayListOf()
                    for (transactionProcessed in it) {
                        val transaction = transactionProcessed.transaction!!
                        if (transaction.name?.lowercase()?.contains(searchTextLowerCase)!!
                            || transaction.date?.lowercase()?.contains(searchTextLowerCase)!!) {
                            filteredData.add(transactionProcessed)
                        }
                    }

                    if (filteredData.isEmpty()) {
                        emptyView?.visibility = View.VISIBLE
                    } else {
                        emptyView?.visibility = View.GONE
                        adapter.update(filteredData)
                    }
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        searchEditText?.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == KeyEvent.KEYCODE_BACK) {
                hideKeyboard()
            }
            false
        }

        searchCloseButton.setOnClickListener {
            searchEditText?.setText("")
            hideKeyboard()
        }

        touchInterceptor.setOnTouchListener { _: View?, motionEvent: MotionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if (searchEditText?.isFocused == true) {
                    hideKeyboard()
                }
            }
            false
        }
    }

    private fun hideKeyboard() {
        searchEditText?.clearFocus()
        val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(searchEditText?.windowToken, 0)
    }

}