package com.kidssavetheocean.fatechanger.policy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.databinding.ItemPolicyStepBinding

class PolicyStepsAdapter : RecyclerView.Adapter<PolicyStepsAdapter.ViewHolder>() {
    private var items: List<String>? = null

    fun setData(data: List<String>){
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPolicyStepBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvNumber.text = (position + 1).toString()
        holder.binding.tvStepContent.text = items?.get(position) ?: ""
    }

    class ViewHolder(val binding: ItemPolicyStepBinding): RecyclerView.ViewHolder(binding.root)

}