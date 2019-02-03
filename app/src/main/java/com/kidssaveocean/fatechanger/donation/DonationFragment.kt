package com.kidssaveocean.fatechanger.donation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R

class DonationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_donation, container, false)

    fun pressDonateButton(view : View) {
        val uri = Uri.parse("https://www.paypal.com")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}
