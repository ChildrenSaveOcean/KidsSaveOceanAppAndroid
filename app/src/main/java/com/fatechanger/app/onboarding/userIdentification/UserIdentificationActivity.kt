package com.fatechanger.app.onboarding.userIdentification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatechanger.app.R
import com.fatechanger.app.databinding.ActivityUserIdentificationBinding
import com.fatechanger.app.firebase.repository.UsersRepo
import com.fatechanger.app.presentation.mvvm.activity.AbstractActivity
import com.fatechanger.app.presentation.mvvm.vm.EmptyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserIdentificationActivity : AbstractActivity<ActivityUserIdentificationBinding, EmptyViewModel>(), UserIdentificaitonRecyclerAdapter.ItemClick {
    private enum class Operator {
        STUDENT,
        TEACHER,
        SUPPORT,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerview: RecyclerView = binding.userIdentificationRecyclerview

        val adapter = UserIdentificaitonRecyclerAdapter(this)
        adapter.setItemClickListener(this)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_user_identification

    override fun getViewModelClass(): Class<EmptyViewModel> = EmptyViewModel::class.java

    override fun OnItemClick(v: View, position: Int) {
        when(position){
            Operator.STUDENT.ordinal-> enterVideoActivity(getString(R.string.students))
            Operator.TEACHER.ordinal -> enterVideoActivity(getString(R.string.teachers))
            Operator.SUPPORT.ordinal -> enterVideoActivity(getString(R.string.others))
        }
        UsersRepo.userModel?.second?.apply {
            user_person_type = position
            UsersRepo.updateOrCreateUser(this)
        }
    }

    fun enterVideoActivity(type: String){
        val intent = Intent(this, IntroductionVideoActivity::class.java)
        intent.putExtra(IntroductionVideoActivity.INTRO_TYPE, type)
        startActivity(intent)
    }
}
