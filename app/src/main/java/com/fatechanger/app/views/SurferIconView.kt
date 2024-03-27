package com.fatechanger.app.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.fatechanger.app.R
import com.fatechanger.app.extensions.setBg

class SurferIconView (context: Context, attrs: AttributeSet) : View(context, attrs)
{
    private var _isActive: Boolean = false
    var isActive: Boolean
        get() = _isActive
        set(value) {
            _isActive = value
            when (_isActive) {
                true -> {
                    if (_isCompleted) {
                        this.setBg(context, R.drawable.task_completed_active_bg)
                    }
                    else {
                        this.setBg(context, R.drawable.task_uncompleted_active_bg)
                    }
                }
                false -> {
                    if (_isCompleted) {
                        this.setBg(context, R.drawable.task_completed_unactive_bg)
                    }
                    else {
                        this.setBg(context, R.drawable.task_uncompleted_unactive_bg, 0.8F)
                    }
                }
            }
        }

    private var _isCompleted : Boolean = false
    var isCompleted: Boolean
        get() = _isCompleted
        set(value) {
            _isCompleted = value
            when (_isCompleted) {
                true -> {
                    if (_isActive) {
                        this.setBg(context, R.drawable.task_completed_active_bg)
                    }
                    else {
                        this.setBg(context, R.drawable.task_completed_unactive_bg)
                    }
                }
                false -> {
                    if (_isActive) {
                        this.setBg(context, R.drawable.task_uncompleted_active_bg)
                    }
                    else {
                        this.setBg(context, R.drawable.task_uncompleted_unactive_bg, 0.8F)
                    }
                }
            }
        }

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SurferIconView,
                0, 0).apply {
            try {
                isCompleted = getBoolean(R.styleable.SurferIconView_isCompleted, false)
                isActive = getBoolean(R.styleable.SurferIconView_isActive, false)
            } finally {
                recycle()
            }
        }
    }
}