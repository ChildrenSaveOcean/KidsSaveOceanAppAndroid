package com.kidssaveocean.fatechanger.policy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidssaveocean.fatechanger.R
import kotlinx.android.synthetic.main.item_policy_step.view.*

class PolicyStepsAdapter: RecyclerView.Adapter<PolicyStepsAdapter.ViewHolder>() {
    lateinit var items: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_policy_step, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tvNumber.text = (position + 1).toString()
        holder.view.tvStepContent.text = items[position]
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}