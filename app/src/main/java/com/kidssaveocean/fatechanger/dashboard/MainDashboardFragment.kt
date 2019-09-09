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
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.views.ActionAlertDialog
import kotlinx.android.synthetic.main.fragment_main_dashboard.*


class MainDashboardFragment : Fragment() {

    private var topLeftSteeringWeelCorner : PointF? = null
    var hasBackground : Boolean = false
    var bottomLeftPoint : PointF? = null

    val isAllPointsInitiated : Boolean
        get() {
            if (bottomLeftPoint == null) return false
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

        val bottomActivity = activity as BottomNavigationActivity
        action_alert_button.setOnClickListener {
            ActionAlertDialog(bottomActivity).show()
        }

        icons.viewTreeObserver.addOnGlobalLayoutListener {
            if (icons != null) {
                bottomLeftPoint = PointF(icons.x, icons.y + icons.height)
                if (!hasBackground && isAllPointsInitiated) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            bottomLeftPoint!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }

        alert_field.viewTreeObserver.addOnGlobalLayoutListener {
            if (alert_field != null) {
                topLeftSteeringWeelCorner = PointF(alert_field.x, alert_field.y)
                if (!hasBackground && isAllPointsInitiated) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            bottomLeftPoint!!,
                            topLeftSteeringWeelCorner!!)
                    hasBackground = true
                }
            }
        }
    }
}

private class BackgroundDrawable(
        val activity : Activity,
        val bottomLeftPoint: PointF,
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
        line.strokeWidth = activity.resources.getDimension(R.dimen.dashboard_stroke_width)//.dpToPx()

        // draw lines points
        val point_1 = PointF(topLeftSteeringWeelCorner.x,  topLeftSteeringWeelCorner.y)
        val point_2 = PointF(topLeftSteeringWeelCorner.x - 100,  topLeftSteeringWeelCorner.y - 100)
        val point_3 = PointF(point_2.x, bottomLeftPoint.y - activity.resources.getDimension(R.dimen.dashboard_stroke_width) / 2)
        val point_4 = PointF(point_3.x + 100,  point_3.y)
        // connecting lines
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
