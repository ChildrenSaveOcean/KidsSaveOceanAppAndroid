package com.kidssaveocean.fatechanger.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kidssaveocean.fatechanger.R
import android.graphics.drawable.Drawable

import android.annotation.SuppressLint
import android.app.Activity
import com.kidssaveocean.fatechanger.extensions.getScreenSize
import android.graphics.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.kidssaveocean.fatechanger.extensions.pxToDp
import kotlinx.android.synthetic.main.fragment_main_dashboard.*


class MainDashboardFragment : Fragment() {

    var hasBackground : Boolean = false
    var centerOfFirstIcon : PointF? = null
    var centerOfSecondIcon : PointF? = null
    var centerOfThirdIcon : PointF? = null
    var centerOfFourthIcon : PointF? = null
    var centerOfFifthIcon : PointF? = null
    var centerOfSixthIcon : PointF? = null
    var topLeftSteeringWeelCorner : PointF? = null

    fun isAllCentersInitiated() : Boolean {
        if (centerOfFirstIcon == null) return false
        if (centerOfSecondIcon == null) return false
        if (centerOfThirdIcon == null) return false
        if (centerOfFourthIcon == null) return false
        if (centerOfFifthIcon == null) return false
        if (centerOfSixthIcon == null) return false
        if (topLeftSteeringWeelCorner == null) return false
        return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (firstIcon != null) {
                centerOfFirstIcon = PointF(firstIcon.x + firstIcon.width / 2, firstIcon.y + firstIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
        secondIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (secondIcon != null) {
                centerOfSecondIcon = PointF(secondIcon.x + secondIcon.width / 2, secondIcon.y + secondIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
        thirdIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (thirdIcon != null) {
                centerOfThirdIcon = PointF(thirdIcon.x + thirdIcon.width / 2, thirdIcon.y + thirdIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
        fourthIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (fourthIcon != null) {
                centerOfFourthIcon = PointF(fourthIcon.x + fourthIcon.width / 2, fourthIcon.y + fourthIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
        fifthIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (fifthIcon != null) {
                centerOfFifthIcon = PointF(fifthIcon.x + fifthIcon.width / 2, fifthIcon.y + fifthIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
        sixthIcon.viewTreeObserver.addOnGlobalLayoutListener {
            if (sixthIcon != null) {
                centerOfSixthIcon = PointF(sixthIcon.x + sixthIcon.width / 2, sixthIcon.y + sixthIcon.height / 2)
                if (!hasBackground && isAllCentersInitiated()){
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }

        alert_field.viewTreeObserver.addOnGlobalLayoutListener {
            if (alert_field != null) {
                topLeftSteeringWeelCorner = PointF(alert_field.x, alert_field.y)
                if (!hasBackground && isAllCentersInitiated()) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            centerOfFirstIcon!!,
                            centerOfSecondIcon!!,
                            centerOfThirdIcon!!,
                            centerOfFourthIcon!!,
                            centerOfFifthIcon!!,
                            centerOfSixthIcon!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
    }
}

private class BackgroundDrawable(
        val activity : Activity,
        val centerOfFirstIcon : PointF,
        val centerOfSecondIcon : PointF,
        val centerOfThirdIcon : PointF,
        val centerOfFourthIcon : PointF,
        val centerOfFifthIcon : PointF,
        val centerOfSixthIcon : PointF,
        val topLeftSteeringWeelCorner : PointF
) : Drawable() {

    override fun draw(canvas: Canvas) {

        val screenSize = activity.getScreenSize()
        val bg = Paint()
        bg.style = Paint.Style.FILL
        bg.color = ContextCompat.getColor(activity, R.color.dashboard_bg_color)
        canvas.drawRect(0F, 0F, screenSize.width.toFloat(), screenSize.height.toFloat(), bg)

        val imageView = ImageView(activity)
        imageView.setImageResource(R.drawable.whale_bg)
        val drawable = imageView.drawable;
        drawable.setBounds(0, (screenSize.height * 0.66).toInt(), screenSize.width, screenSize.height);
        drawable.draw(canvas)

        val line = Paint()
        line.color = Color.WHITE
        line.style = Paint.Style.STROKE
        line.strokeWidth = activity.resources.getDimension(R.dimen.dashboard_stroke_width).pxToDp()

        val iconline_height_ratio = 1.2F
        val iconPadding = activity.resources.getDimension(R.dimen.dashboard_icon_margin)
        val centerYOffset = activity.resources.getDimension(R.dimen.dashboard_icon_size)

        // draw icon lines
        canvas.drawLine(centerOfFirstIcon.x + iconPadding, centerOfFirstIcon.y + centerYOffset, centerOfFirstIcon.x + iconPadding,  centerOfFirstIcon.y + centerYOffset * iconline_height_ratio, line)
        canvas.drawLine(centerOfSecondIcon.x + iconPadding, centerOfSecondIcon.y + centerYOffset, centerOfSecondIcon.x + iconPadding,  centerOfSecondIcon.y + centerYOffset * iconline_height_ratio, line)
        canvas.drawLine(centerOfThirdIcon.x + iconPadding, centerOfThirdIcon.y + centerYOffset, centerOfThirdIcon.x + iconPadding,  centerOfThirdIcon.y + centerYOffset * iconline_height_ratio, line)
        canvas.drawLine(centerOfFourthIcon.x + iconPadding, centerOfFourthIcon.y + centerYOffset, centerOfFourthIcon.x + iconPadding,  centerOfFourthIcon.y + centerYOffset * iconline_height_ratio, line)
        canvas.drawLine(centerOfFifthIcon.x + iconPadding, centerOfFifthIcon.y + centerYOffset, centerOfFifthIcon.x + iconPadding,  centerOfFifthIcon.y + centerYOffset * iconline_height_ratio, line)
        canvas.drawLine(centerOfSixthIcon.x + iconPadding, centerOfSixthIcon.y + centerYOffset, centerOfSixthIcon.x + iconPadding,  centerOfSixthIcon.y + centerYOffset * iconline_height_ratio, line)
        // connecting lines
        val point_1 = PointF(topLeftSteeringWeelCorner.x,  topLeftSteeringWeelCorner.y)
        val point_2 = PointF(topLeftSteeringWeelCorner.x - 100,  topLeftSteeringWeelCorner.y - 100)
        val point_3 = PointF(point_2.x, centerOfFirstIcon.y + centerYOffset * iconline_height_ratio)
        val point_4 = PointF(centerOfSixthIcon.x + iconPadding,  centerOfSixthIcon.y + centerYOffset * iconline_height_ratio)
        canvas.drawLine(point_1.x, point_1.y, point_2.x, point_2.y, line)
        canvas.drawLine(point_2.x, point_2.y, point_3.x, point_3.y, line)
        canvas.drawLine(point_3.x, point_3.y, point_4.x, point_4.y, line)
    }

    @SuppressLint("WrongConstant")
    override fun getOpacity(): Int {
        return 0
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(cf: ColorFilter?) {}
}
