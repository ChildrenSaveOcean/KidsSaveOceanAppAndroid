package com.kidssaveocean.fatechanger.map

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.firebase.model.CountryModel

class CountryListAdpter(private val context: Context)
    : RecyclerView.Adapter<CountryListAdpter.ViewHoder>() {

    var countriesLocal: MutableList<CountryModel> = mutableListOf()
        set(value) {
            val number = if (value.size > 10) 10 else value.size
            field.addAll(value.sortedByDescending { it.letters_written_to_country }.take(number))
        }

    private val colorList = listOf(
            R.color.rank_1,
            R.color.rank_2,
            R.color.rank_3,
            R.color.rank_4,
            R.color.rank_5,
            R.color.rank_6,
            R.color.rank_7,
            R.color.rank_8,
            R.color.rank_9,
            R.color.rank_10
    )

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val view: View = inflater.inflate(R.layout.country_list_item, parent, false)
        return ViewHoder(view)
    }

    override fun getItemCount(): Int = countriesLocal.size

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        holder.rankNumber.text = "${position + 1}"
        holder.grad.setColor(context.resources.getColor(colorList[position]))

        holder.countryName.text = countriesLocal[position].country_name
        holder.countryLetterNumber.text = countriesLocal[position].letters_written_to_country.toString()

    }


    class ViewHoder(val view: View) : RecyclerView.ViewHolder(view) {
        var rankNumber: TextView = view.findViewById(R.id.rank_number)
        var grad: GradientDrawable = rankNumber.background as GradientDrawable
        var countryName: TextView = view.findViewById(R.id.country_name)
        var countryLetterNumber: TextView = view.findViewById(R.id.country_letter_number)

    }
}