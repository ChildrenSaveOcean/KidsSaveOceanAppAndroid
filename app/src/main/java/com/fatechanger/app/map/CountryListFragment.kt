package com.fatechanger.app.map

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatechanger.app.BR
import com.fatechanger.app.R
import com.fatechanger.app.databinding.FragmentCountryListBinding
import com.fatechanger.app.firebase.FirebaseService
import com.fatechanger.app.firebase.model.CountryModel
import com.fatechanger.app.presentation.mvvm.fragment.AbstractFragment
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Observable
import java.util.Observer

@AndroidEntryPoint
class CountryListFragment : AbstractFragment<FragmentCountryListBinding, EmptyViewModel>(), Observer {

    private var countries: List<CountryModel> = listOf()
    private lateinit var mAdapter: CountryListAdapter

    override fun onPrepareLayout(layoutView: View?) {
        super.onPrepareLayout(layoutView)
        val recyclerview: RecyclerView = binding.countryListRv

        //todo get rid of this
        FirebaseService.getInstance().addObserver(this)

        mAdapter = CountryListAdapter(activity as Context)
        recyclerview.adapter = mAdapter

        recyclerview.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(object : DividerItemDecoration(activity, VERTICAL) {})
        }

        updateList(FirebaseService.getInstance().countries)
    }

    override fun getViewModelResId(): Int = BR.emptyVM

    override fun getLayoutResId(): Int = R.layout.fragment_country_list

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

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
