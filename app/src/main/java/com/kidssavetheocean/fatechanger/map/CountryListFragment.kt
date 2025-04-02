package com.kidssavetheocean.fatechanger.map

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.databinding.FragmentCountryListBinding
import com.kidssavetheocean.fatechanger.firebase.FirebaseService
import com.kidssavetheocean.fatechanger.firebase.model.CountryModel
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountryListFragment : AbstractFragment<FragmentCountryListBinding, EmptyViewModel>() {

    private lateinit var mAdapter: CountryListAdapter
    @Inject
    lateinit var firebaseService: FirebaseService

    override fun onPrepareLayout(layoutView: View?) {
        val recyclerview: RecyclerView = binding.countryListRv

        mAdapter = CountryListAdapter(activity as Context)
        recyclerview.adapter = mAdapter

        recyclerview.run {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(object : DividerItemDecoration(activity, VERTICAL) {})
        }

        firebaseService.countries.observe(this.viewLifecycleOwner) {
            updateList(it)
        }
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
}
