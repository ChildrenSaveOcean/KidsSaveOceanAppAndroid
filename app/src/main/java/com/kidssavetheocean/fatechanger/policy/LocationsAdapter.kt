package com.kidssavetheocean.fatechanger.policy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.databinding.ItemLocationBinding
import com.kidssavetheocean.fatechanger.firebase.model.HijackPolicyLocationModel

class LocationsAdapter: RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {
    private var policyLocations: List<Pair<String, HijackPolicyLocationModel>>? = null
    private lateinit var onItemCLickedListener: OnItemCLickedListener

    fun setPolicyLocations(data: List<Pair<String, HijackPolicyLocationModel>>?){
        policyLocations = data
        notifyDataSetChanged()
    }

    interface OnItemCLickedListener{
        fun onItemClicked(view: View, position: Int)
    }

    fun setOnItemClickedListener(listner: OnItemCLickedListener){
        onItemCLickedListener = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater, parent, false)
        return LocationsViewHolder(binding)
    }

    override fun getItemCount(): Int = policyLocations?.size ?: 0

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {

        holder.binding.tvLocation.text = policyLocations?.get(position)?.second?.location ?: ""
        holder.binding.tvLocation.setOnClickListener{
            onItemCLickedListener.onItemClicked(it, position)
        }
    }

    class LocationsViewHolder(val binding: ItemLocationBinding) : RecyclerView.ViewHolder(binding.root)
}