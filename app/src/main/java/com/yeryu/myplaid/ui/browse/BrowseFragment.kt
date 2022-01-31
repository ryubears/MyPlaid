package com.yeryu.myplaid.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yeryu.myplaid.R
import com.yeryu.myplaid.api.data.AccountProcessed
import com.yeryu.myplaid.ui.MainActivity
import java.util.*

class BrowseFragment : Fragment() {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        // Setup recycler view.
        val recyclerView: RecyclerView = rootView.findViewById(R.id.browse_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val adapter = BrowseListAdapter()
        recyclerView.adapter = adapter

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(rootView.context, R.drawable.default_list_divider)!!)
        recyclerView.addItemDecoration(divider)

        if (arguments != null) {
            val processedData = arguments?.getSerializable(MainActivity.processedDataKey) as ArrayList<AccountProcessed>
            adapter.update(processedData)
        }

        return rootView
    }

}