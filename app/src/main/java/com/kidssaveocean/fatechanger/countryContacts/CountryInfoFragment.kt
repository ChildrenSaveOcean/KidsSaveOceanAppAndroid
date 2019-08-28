package com.kidssaveocean.fatechanger.countryContacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.kidssaveocean.fatechanger.R

class CountryInfoFragment : Fragment() {

    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            address = it.getString("address")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_info, container, false)
        var textView = view.findViewById(R.id.adress_text) as TextView
        textView.text = address
        return view
    }
}