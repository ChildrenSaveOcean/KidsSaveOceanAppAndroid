package com.kidssavetheocean.fatechanger.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.kidssavetheocean.fatechanger.R
import kotlin.math.sqrt

class CustomMapMarkerView : View {

    // In order to avoid the OOM
    companion object {
        var numberLetter: String = ""
    }

    private var leftPoint: PointF? = null
    private var rightPoint: PointF? = null
    private var bottomPoint: PointF? = null
    private var controlPoint: PointF? = null

    private val radius = 25f
    private val mCircleCenter = PointF(radius, radius)
    private var leftX: Float = 0.0F
    private var leftY: Float = 0.0F

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(resources.getDimension(R.dimen.map_marker_width).toInt(), resources.getDimension(R.dimen.map_marker_height).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val value: Float = (1 / sqrt(2.0)).toFloat()

        leftX = (radius * (1 - value))
        leftY = (radius * (1 + value))

        leftPoint = PointF(leftX, leftY)
        rightPoint = PointF(leftY, leftY)

        bottomPoint = PointF(radius, radius * 4)
        controlPoint = PointF(radius, radius * 2.5f)


        val paint = Paint()
        paint.color = Color.RED

        paint.isAntiAlias = true
        canvas.drawCircle(mCircleCenter.x, mCircleCenter.y, radius, paint)

        val path = Path()
        paint.color = Color.RED

        path.moveTo(bottomPoint!!.x, bottomPoint!!.y)
        path.quadTo(controlPoint!!.x, controlPoint!!.y, leftPoint!!.x, leftPoint!!.y)
        path.lineTo(rightPoint!!.x, rightPoint!!.y)
        path.quadTo(controlPoint!!.x, controlPoint!!.y, bottomPoint!!.x, bottomPoint!!.y)
        path.close()
        canvas.drawPath(path, paint)

        paint.color = Color.WHITE
        paint.textSize = 25f
        canvas.drawText(numberLetter, 8f, radius + 10, paint)

    }


}
