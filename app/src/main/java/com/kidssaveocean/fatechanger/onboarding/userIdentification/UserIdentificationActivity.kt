package com.kidssaveocean.fatechanger.onboarding.userIdentification

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidssaveocean.fatechanger.R

class UserIdentificationActivity : AppCompatActivity(), UserIdentificaitonRecyclerAdapter.ItemClick {
    private enum class Operator {
        STUDENT,
        TEACHER,
        SUPPORT,
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_identification)

        val recyclerview: RecyclerView = findViewById(R.id.user_identification_recyclerview)

        var adapter = UserIdentificaitonRecyclerAdapter(this)
        adapter.setItemClickListener(this)

        recyclerview?.adapter = adapter
        recyclerview?.layoutManager = LinearLayoutManager(this)
    }

    override fun OnItemClick(v: View, position: Int) {
        when(position){
            Operator.STUDENT.ordinal-> enterVideoActivity(getString(R.string.students))
            Operator.TEACHER.ordinal -> enterVideoActivity(getString(R.string.teachers))
            Operator.SUPPORT.ordinal -> enterVideoActivity(getString(R.string.others))
        }
    }

    fun enterVideoActivity(type: String){
        val intent = Intent(this, IntroductionVideoActivity::class.java)
        intent.putExtra(IntroductionVideoActivity.INTRO_TYPE, type)
        startActivity(intent)
    }
}
