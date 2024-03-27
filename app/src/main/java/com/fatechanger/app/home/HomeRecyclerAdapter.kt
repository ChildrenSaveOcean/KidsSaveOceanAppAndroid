package com.fatechanger.app.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatechanger.app.R

class HomeRecyclerAdapter(private val context: Context)
    : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var itemClick: ItemClick? = null
    private val imageIds = listOf(
            R.drawable.immerse,
            R.drawable.experience_card_impact,
            R.drawable.experience_card_intro,
            R.drawable.experience_activist_dashboard,
            R.drawable.experience_card_watch,
            R.drawable.share_like_a_boss
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.home_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imageIds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
                .load(imageIds[position])
                .into(holder.imageView)
        holder.imageView.setOnClickListener {
            itemClick!!.onItemClick(holder.imageView, position) }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.iv_home_recycler)

    }

    fun setItemClickListener(itemClick : ItemClick){
        this.itemClick = itemClick
    }

    interface ItemClick {
        fun onItemClick(v: View, position: Int);
    }

}