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
import android.content.Context
import android.content.Intent
import com.kidssaveocean.fatechanger.extensions.getScreenSize
import android.graphics.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_main_dashboard.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.kidssaveocean.fatechanger.bottomNavigation.BottomNavigationActivity
import com.kidssaveocean.fatechanger.countryContacts.CountryIntroFragment
import com.kidssaveocean.fatechanger.extensions.setImage
import com.kidssaveocean.fatechanger.extensions.addToNavigationStack
import com.kidssaveocean.fatechanger.views.ActionAlertDialog
import io.reactivex.subjects.PublishSubject


class MainDashboardFragment : Fragment() {

    private var currentCircleAngle : Float = 0f
    private var currentMeteorAngle : Float = 0f
    private var topLeftSteeringWeelCorner : PointF? = null
    private var bottomLeftPoint : PointF? = null

    private var stepOne : DashboardTask = DashboardTask(DashboardSteps.RESEARCH, false)
    private var stepTwo : DashboardTask = DashboardTask(DashboardSteps.WRITE_LETTER, false)
    private var stepThree : DashboardTask = DashboardTask(DashboardSteps.SHARING, false)
    private var stepFour : DashboardTask = DashboardTask(DashboardSteps.LETTER_CAMPAING, false)
    private var stepFive : DashboardTask = DashboardTask(DashboardSteps.GOVERNMENT, false)
    private var stepSix : DashboardTask = DashboardTask(DashboardSteps.PROTEST, false)

    private var currentTask : DashboardTask = DashboardTask(DashboardSteps.WRITE_LETTER, false)

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

        changeVisuals(currentTask.type!!)

        currentTask?.completedObservable?.subscribe {
            changeCompletedStatus(bottomActivity, it)
            when (currentTask.type) {
                DashboardSteps.RESEARCH -> stepOne.isCompleted = it
                DashboardSteps.WRITE_LETTER -> stepTwo.isCompleted = it
                DashboardSteps.SHARING -> stepThree.isCompleted = it
                DashboardSteps.LETTER_CAMPAING -> stepFour.isCompleted = it
                DashboardSteps.GOVERNMENT -> stepFive.isCompleted = it
                DashboardSteps.PROTEST -> stepSix.isCompleted = it
            }
        }

        action_alert_button.setOnClickListener {
            ActionAlertDialog(bottomActivity).show()
        }

        firstIcon.setOnClickListener {
            clickOnSurfer(stepOne)
        }

        secondIcon.setOnClickListener {
            clickOnSurfer(stepTwo)
        }

        thirdIcon.setOnClickListener {
            clickOnSurfer(stepThree)
        }

        fourthIcon.setOnClickListener {
            clickOnSurfer(stepFour)
        }

        fifthIcon.setOnClickListener {
            clickOnSurfer(stepFive)
        }

        sixthIcon.setOnClickListener {
            clickOnSurfer(stepSix)
        }

        how_button?.setOnClickListener {
            howButtonAction(currentTask?.type!!)
        }

        I_did_it_button?.setOnClickListener {
            currentTask?.isCompleted = currentTask?.isCompleted?.not()
        }

        icons.viewTreeObserver.addOnGlobalLayoutListener {
            if (icons != null) {
                bottomLeftPoint = PointF(icons.x, icons.y + icons.height)
                if (isAllPointsInitiated) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            bottomLeftPoint!!,
                            topLeftSteeringWeelCorner!!)
                }
            }
        }

        alert_field.viewTreeObserver.addOnGlobalLayoutListener {
            if (alert_field != null) {
                topLeftSteeringWeelCorner = PointF(alert_field.x, alert_field.y)
                if (isAllPointsInitiated) {
                    view.background = BackgroundDrawable(
                            activity as Activity,
                            bottomLeftPoint!!,
                            topLeftSteeringWeelCorner!!)
                }
            }
        }
    }

    private fun clickOnSurfer(step : DashboardTask) {
        currentTask.type = step.type
        currentTask.isCompleted = step.isCompleted
        changeVisuals(currentTask.type!!)
    }

    private fun changeVisuals(step : DashboardSteps) {
        firstIcon.isActive = step.ordinal == 0
        secondIcon.isActive = step.ordinal == 1
        thirdIcon.isActive = step.ordinal == 2
        fourthIcon.isActive = step.ordinal == 3
        fifthIcon.isActive = step.ordinal == 4
        sixthIcon.isActive = step.ordinal == 5
        rotateWheel(step)
        rotateMeteor(step)
        when (step) {
            DashboardSteps.RESEARCH -> task_description?.text = getString(R.string.research_task_description)
            DashboardSteps.WRITE_LETTER -> task_description?.text = getString(R.string.write_letter_task_description)
            DashboardSteps.SHARING -> task_description?.text = getString(R.string.sharing_task_description)
            DashboardSteps.LETTER_CAMPAING -> task_description?.text = getString(R.string.letter_campaing_task_description)
            DashboardSteps.GOVERNMENT -> task_description?.text = getString(R.string.government_task_description)
            DashboardSteps.PROTEST -> task_description?.text = getString(R.string.protest_task_description)
        }
    }

    private fun rotateWheel(step : DashboardSteps) {
        when (step.ordinal) {
            0,1,2,3,4,5 -> {
                val angleStep = 50f
                val newAngle = step.ordinal * angleStep
                val rotate = RotateAnimation(
                        currentCircleAngle,
                        newAngle,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                )
                rotate.fillAfter = true
                rotate.duration = 1000
                floating_area.startAnimation(rotate)
                currentCircleAngle = newAngle
            }
            else -> println("Wrong position number")
        }
    }

    private fun rotateMeteor(step : DashboardSteps) {
        when (step.ordinal) {
            0,1,2,3,4,5 -> {
                val angleStep = 30f
                val newAngle = (step.ordinal + 1) * angleStep
                val rotate = RotateAnimation(
                        currentMeteorAngle,
                        newAngle,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                )
                rotate.fillAfter = true
                rotate.duration = 1000
                meteor_ball.startAnimation(rotate)
                currentMeteorAngle = newAngle
            }
            else -> println("Wrong position number")
        }
    }

    private fun changeCompletedStatus(context : Context, completed : Boolean) {
        when(completed) {
            true -> {
                complete_image.setImage(context, R.drawable.complete_fist_and_writing)
                I_did_it_button.setImage(context, R.drawable.not_yet_button)
            }
            false -> {
                complete_image.setImage(context, R.drawable.incomplete_fist_and_writing)
                I_did_it_button.setImage(context, R.drawable.i_did_it_button)
            }
        }
        when (currentTask.type) {
            DashboardSteps.RESEARCH -> firstIcon.isCompleted = completed
            DashboardSteps.WRITE_LETTER -> secondIcon.isCompleted = completed
            DashboardSteps.SHARING -> thirdIcon.isCompleted = completed
            DashboardSteps.LETTER_CAMPAING -> fourthIcon.isCompleted = completed
            DashboardSteps.GOVERNMENT -> fifthIcon.isCompleted = completed
            DashboardSteps.PROTEST -> sixthIcon.isCompleted = completed
            else -> println("Unknown type for DashboardSteps")
        }
    }

    private fun howButtonAction(step : DashboardSteps) {
        val bottomActivity = activity as BottomNavigationActivity
        when (step) {
            DashboardSteps.RESEARCH -> bottomActivity.setMenuItem(R.id.action_resources)
            DashboardSteps.WRITE_LETTER -> {
                CountryIntroFragment().addToNavigationStack(
                        bottomActivity.supportFragmentManager,
                        R.id.fragment_container,
                        "country_fragment")
            }
            DashboardSteps.SHARING -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

            }
            DashboardSteps.LETTER_CAMPAING -> {}
            DashboardSteps.GOVERNMENT -> {

            }
            DashboardSteps.PROTEST -> {
                NobodysListeningFragment().addToNavigationStack(
                        bottomActivity.supportFragmentManager,
                        R.id.fragment_container,
                        "nobody_listening_fragment")
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

class DashboardTask(dType : DashboardSteps, dIsCompleted : Boolean) {

    private var _type : DashboardSteps? = null
    private var _isCompleted : Boolean? = null

    val typeObservable = PublishSubject.create<DashboardSteps>()
    val completedObservable = PublishSubject.create<Boolean>()

    var type : DashboardSteps?
        get() = _type
        set(value) {
            _type = value
            _type?.let { typeObservable.onNext(it) }
        }

    var isCompleted : Boolean?
        get() = _isCompleted
        set(value) {
            _isCompleted = value
            _isCompleted?.let { completedObservable.onNext(it) }
        }

    init {
        _type = dType
        _isCompleted = dIsCompleted
    }
}

enum class DashboardSteps {
    RESEARCH,
    WRITE_LETTER,
    SHARING,
    LETTER_CAMPAING,
    GOVERNMENT,
    PROTEST
}
