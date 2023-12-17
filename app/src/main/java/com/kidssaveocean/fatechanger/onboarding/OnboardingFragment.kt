package com.kidssaveocean.fatechanger.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.onboarding.userIdentification.UserIdentificationActivity

//todo what is this abomination ?????
class OnboardingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val bundle = arguments
        val imageId = bundle!!.getInt(IMAGE)
        val titleTextId = bundle.getInt(TITLE)
        val subTitleTextId = bundle.getInt(SUBTITLE)
        val descriptionTextId = bundle.getInt(DESCRIPTION)
        val layoutId = bundle.getInt(LAYOUT)
        val extraId = bundle.getInt(EXTRA)

        val layout = inflater.inflate(layoutId, container, false) as ScrollView

        layout.findViewById<ImageView>(R.id.onboarding_image).setImageDrawable(resources.getDrawable(imageId))
        layout.findViewById<TextView>(R.id.title).setText(titleTextId)
        layout.findViewById<TextView>(R.id.subtitle).setText(subTitleTextId)
        layout.findViewById<TextView>(R.id.description).setText(descriptionTextId)
        layout.findViewById<TextView>(R.id.extra).setText(extraId)

        layout.findViewById<Button?>(R.id.start_button)?.setOnClickListener { view ->
            val intent = Intent(activity, UserIdentificationActivity::class.java)
            startActivity(intent)
        }

        return layout
    }

    companion object {

        val IMAGE = "image"
        val TITLE = "title"
        val SUBTITLE = "subtitle"
        val DESCRIPTION = "description"
        val LAYOUT = "layout"
        val EXTRA = "extra"
    }


}
