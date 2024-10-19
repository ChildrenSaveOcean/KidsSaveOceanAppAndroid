package com.kidssavetheocean.fatechanger.policy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kidssavetheocean.fatechanger.R
import kotlinx.android.synthetic.main.item_policy_step.view.tvNumber
import kotlinx.android.synthetic.main.item_policy_step.view.tvStepContent

class PolicyStepsAdapter : RecyclerView.Adapter<PolicyStepsAdapter.ViewHolder>() {
    private var items: List<String>? = null

    fun setData(data: List<String>){
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_policy_step, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tvNumber.text = (position + 1).toString()
        holder.view.tvStepContent.text = items?.get(position) ?: ""
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

}