package com.kidssavetheocean.fatechanger.policy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.R
import kotlinx.android.synthetic.main.fragment_locations.rcvLocations

class LocationsDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationsAdapter = LocationsAdapter()
        val pccActivity = activity as PolicyControlCenterActivity
        locationsAdapter.setPolicyLocations(pccActivity.policyLocations)
        rcvLocations.layoutManager = LinearLayoutManager(activity)
        rcvLocations.adapter = locationsAdapter
        rcvLocations.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))

        locationsAdapter.setOnItemClickedListener(object : LocationsAdapter.OnItemCLickedListener{
            override fun onItemClicked(view: View, position: Int) {
                pccActivity.selectedLocation(position)
                this@LocationsDialogFragment.dismiss()
            }

        })
    }
}