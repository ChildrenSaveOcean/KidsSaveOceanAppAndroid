package com.kidssaveocean.fatechanger.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.firebase.FirebaseService
import com.kidssaveocean.fatechanger.firebase.model.CountryModel
import java.util.*

class CountryListFragment : Fragment(), Observer {

    private var countries: List<CountryModel> = listOf()
    private lateinit var mAdapter: CountryListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)
        val recyclerview: RecyclerView = view.findViewById(R.id.country_list_rv)

        FirebaseService.getInstance().addObserver(this)

        mAdapter = CountryListAdapter(activity as Context)
        recyclerview.adapter = mAdapter

        recyclerview.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(object : DividerItemDecoration(activity, VERTICAL) {})
        }

        updateList(FirebaseService.getInstance().countries)

        return view
    }

    private fun updateList(data: List<CountryModel>) {
        if (data.isNotEmpty()) {
            mAdapter.countriesLocal = data as MutableList<CountryModel>
            mAdapter.notifyDataSetChanged()
        }

    }

    override fun update(o: Observable?, arg: Any?) {
        when (o) {
            is FirebaseService -> {
                updateList(o.countries)
            }
        }
    }

}
