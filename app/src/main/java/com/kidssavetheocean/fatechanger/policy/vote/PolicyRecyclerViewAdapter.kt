package com.kidssavetheocean.fatechanger.policy.vote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.databinding.LayoutPolicyNameBinding

class PolicyRecyclerViewAdapter(
    val names: List<String>
):
    RecyclerView.Adapter<PolicyRecyclerViewAdapter.PolicyViewHolder>() {



    class PolicyViewHolder(val binding: LayoutPolicyNameBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutPolicyNameBinding.inflate(layoutInflater, parent, false)
        return PolicyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: PolicyViewHolder, position: Int) {
        val realPos = position % names.size
        with(holder.binding){
            policyNameText.text = names[realPos]
        }
    }
}