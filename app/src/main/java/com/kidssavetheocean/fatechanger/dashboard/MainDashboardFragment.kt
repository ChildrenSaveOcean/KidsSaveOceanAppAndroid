package com.kidssavetheocean.fatechanger.dashboard

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.kidssavetheocean.fatechanger.BR
import com.kidssavetheocean.fatechanger.Constants
import com.kidssavetheocean.fatechanger.R
import com.kidssavetheocean.fatechanger.WebViewActivity
import com.kidssavetheocean.fatechanger.databinding.FragmentMainDashboardNewBinding
import com.kidssavetheocean.fatechanger.extensions.setImage
import com.kidssavetheocean.fatechanger.presentation.mvvm.fragment.AbstractFragment
import com.kidssavetheocean.fatechanger.utility.getSerializableCompat
import com.kidssavetheocean.fatechanger.views.ActionAlertDialog
import com.kidssavetheocean.fatechanger.views.SurferIconView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MainDashboardFragment :
    AbstractFragment<FragmentMainDashboardNewBinding, MainDashboardViewModel>() {

    private var currentCircleAngle: Float = 0f
    private var currentMeteorAngle: Float = 0f

    private lateinit var surferIcons: List<Pair<DashboardSteps, SurferIconView>>
    private val mp = MediaPlayer()

    override fun onResume() {
        super.onResume()
        loadDashboardInfo()
    }

    override fun getViewModelResId(): Int = BR.dashboardViewModel

    override fun getLayoutResId(): Int = R.layout.fragment_main_dashboard_new

    override fun getViewModelClass(): Class<MainDashboardViewModel> = MainDashboardViewModel::class.java

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            val step = args.getSerializableCompat<DashboardSteps>(DASHBOARD_STEP_KEY)
            step?.let {
                viewModel.saveLastSelectedIcon(it)
            }
        }
        binding.apply {
            surferIcons = listOf(
                Pair(DashboardSteps.RESEARCH, one),
                Pair(DashboardSteps.WRITE_LETTER, two),
                Pair(DashboardSteps.LETTER_CAMPAIGN, three),
                Pair(DashboardSteps.SHARING, four),
                Pair(DashboardSteps.GOVERNMENT, five),
                Pair(DashboardSteps.PROTEST, six)
            )
            loadDashboardInfo()
            alertField.actionAlertButton.setOnClickListener {
                ActionAlertDialog(requireContext()).show()
            }
            surferIcons.forEach { pair ->
                pair.second.setOnClickListener {
                    playClick()
                    clickOnSurfer(pair.first)
                    changeTextOnButtonAndFistIcon(pair.second.isCompleted)
                }
            }

            dashboardLayout.dashboardHowButton.setOnClickListener {
                howButtonAction(viewModel.lastSelected)
            }
            dashboardLayout.dashboardTaskStatusButton.setOnClickListener {
                viewModel.updateTaskStatus()?.let {
                    updateSurferCompletedStatus(it)
                    changeTextOnButtonAndFistIcon(it)
                }
            }

            link.setOnClickListener {
                startActivity(Intent(requireActivity(), DashBoardVideoActivity::class.java))
            }
        }
    }

    private fun clickOnSurfer(step: DashboardSteps) {
        viewModel.saveLastSelectedIcon(step)
        rotateWheel(step)
        rotateMeteor(step)
        changeDescriptionText(step)
        activateIcon(step)
    }

    private fun rotateWheel(step: DashboardSteps) {
        when (step.ordinal) {
            0, 1, 2, 3, 4, 5 -> {
                val angleStep = 51.4f
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
                binding.alertField.wheelIndicator.startAnimation(rotate)
                currentCircleAngle = newAngle
            }

            else -> println("Wrong position number")
        }
    }

    private fun rotateMeteor(step: DashboardSteps) {
        when (step.ordinal) {
            0, 1, 2, 3, 4, 5 -> {
                val angleStep = 36.0f
                val newAngle = (step.ordinal) * angleStep
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
                binding.dashboardLayout.meterScaleIndicator.startAnimation(rotate)
                currentMeteorAngle = newAngle
            }

            else -> println("Wrong position number")
        }
    }

    private fun activateIcon(step: DashboardSteps) =
        surferIcons.forEach {
            it.second.isActive = it.first == step
        }

    private fun updateSurferCompletedStatus(status: Boolean) {
        val step = viewModel.lastSelected
        surferIcons.find { it.first == step }?.let {
            it.second.isCompleted = status
        }
    }

    private fun changeTextOnButtonAndFistIcon(completed: Boolean) {
        when (completed) {
            true -> {
                binding.dashboardLayout.completeImage.setImage(requireContext(), R.drawable.complete_fist_and_writing)
                binding.dashboardLayout.dashboardTaskStatusButton.text = getString(R.string.Not_yet)
                binding.dashboardLayout.dashboardTaskStatusText.text = getString(R.string.dashboard_layout_task_status_complete)
            }

            false -> {
                binding.dashboardLayout.completeImage.setImage(requireContext(), R.drawable.incomplete_fist_and_writing)
                binding.dashboardLayout.dashboardTaskStatusButton.text = getString(R.string.I_did_it)
                binding.dashboardLayout.dashboardTaskStatusText.text = getString(R.string.dashboard_layout_task_status_incomplete)
            }
        }
    }

    private fun changeDescriptionText(task: DashboardSteps) {
        binding.dashboardLayout.dashboardTaskDescription.text = getString(task.res)
    }

    private fun howButtonAction(step: DashboardSteps) {
        when (step) {
            DashboardSteps.RESEARCH -> {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(Constants.INTENT_URL, Constants.URL_STUDENT_RESOURCES)
                startActivity(intent)
            }

            DashboardSteps.WRITE_LETTER -> {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(Constants.INTENT_URL, Constants.URL_STUDENT_RESOURCES)
                startActivity(intent)
            }

            DashboardSteps.SHARING -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://pederhill.wixsite.com/kids-save-ocean")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            DashboardSteps.LETTER_CAMPAIGN -> {
                navigateToView(StartActivistCampaignFragment::class)
            }

            DashboardSteps.GOVERNMENT -> {
                navigateToView(EngageLocalGovernmentFragment::class)
            }

            DashboardSteps.PROTEST -> {
                navigateToView(NobodysListeningFragment::class)
            }
        }
    }

    private fun loadDashboardInfo() {
        surferIcons.forEach { pair ->
            viewModel.tasks.find { it.type == pair.first }?.let {
                pair.second.isCompleted = it.getIsCompleted()
            }
        }
        clickOnSurfer(viewModel.lastSelected)
    }

    private fun playClick() {
        if (mp.isPlaying) {
            mp.stop()
        }
        try {
            mp.reset()
            val afd: AssetFileDescriptor = requireContext().assets.openFd("knobClick.mp3")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            mp.prepare()
            mp.start()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val DASHBOARD_STEP_KEY = "dashboard_step"
    }
}

enum class DashboardSteps(val res: Int) {
    RESEARCH(R.string.research_task_description),
    WRITE_LETTER(R.string.write_letter_task_description),
    LETTER_CAMPAIGN(R.string.sharing_task_description),
    SHARING(R.string.write_letter_task_climate),
    GOVERNMENT(R.string.government_task_description),
    PROTEST(R.string.protest_task_description),
}
