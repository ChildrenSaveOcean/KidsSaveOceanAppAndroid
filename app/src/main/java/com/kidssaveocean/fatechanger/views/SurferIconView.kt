package com.kidssaveocean.fatechanger.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.kidssaveocean.fatechanger.R
import com.kidssaveocean.fatechanger.extensions.setBg

class SurferIconView (context: Context, attrs: AttributeSet) : View(context, attrs)
{
    var _isActive: Boolean = false
    var isActive: Boolean
        get() = _isActive
        set(value) {
            _isActive = value
            if (value) {
                this.setBg(context, R.drawable.surfer_icon_active_bg)

            }
            else {
                this.setBg(context, R.drawable.dashboard_red_icon, 0.8F)
            }
        }

    var _status : SurferIconStatus = SurferIconStatus.UNCOMPLETED
    var status: SurferIconStatus
        get() = _status
        set(value) {
            _status = value
            when (value) {
                SurferIconStatus.UNCOMPLETED -> this.setBg(context, R.drawable.dashboard_red_icon)
                SurferIconStatus.COMPLETED -> this.setBg(context, R.drawable.surfer_icon_default_bg)
            }
        }

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SurferIconView,
                0, 0).apply {
            try {
                status = SurferIconStatus.values()[getInteger(R.styleable.SurferIconView_status, 0)]
                isActive = getBoolean(R.styleable.SurferIconView_isActive, false)
            } finally {
                recycle()
            }
        }
    }
}

enum class SurferIconStatus {
    UNCOMPLETED, COMPLETED
}