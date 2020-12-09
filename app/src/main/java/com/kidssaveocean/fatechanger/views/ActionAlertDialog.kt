package com.kidssaveocean.fatechanger.views

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import com.kidssaveocean.fatechanger.R

class ActionAlertDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.action_alert_popup)
    }
}