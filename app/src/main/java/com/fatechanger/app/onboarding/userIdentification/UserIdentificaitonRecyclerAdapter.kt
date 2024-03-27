package com.fatechanger.app.onboarding.userIdentification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fatechanger.app.R

class UserIdentificaitonRecyclerAdapter(private val context: Context)
    : RecyclerView.Adapter<UserIdentificaitonRecyclerAdapter.ViewHoder>() {
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var itemClick: ItemClick? = null
    private val imageIds = listOf(
            R.drawable.experience_card_student,
            R.drawable.experience_card_teacher,
            R.drawable.experience_card_support
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val view: View = inflater.inflate(R.layout.user_identification_recycler_item, parent, false)
        return ViewHoder(view)
    }

    override fun getItemCount(): Int = imageIds.size

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        Glide.with(context)
                .load(imageIds[position])
                .into(holder.imageView)
        holder.imageView.setOnClickListener {
            itemClick!!.OnItemClick(holder.imageView, position) }
    }


    class ViewHoder(val view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.iv_home_recycler)

    }

    fun setItemClickListener(itemClick : ItemClick){
        this.itemClick = itemClick
    }

    interface ItemClick {
        fun OnItemClick(v: View, position: Int);
    }

}