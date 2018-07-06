package com.kidssaveocean.fatechanger.userIdentification;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.kidssaveocean.fatechanger.R;
import butterknife.ButterKnife;


public class UserIdentificationActivity extends AppCompatActivity {

    UserIdentificationFragment studentFragment;
    UserIdentificationFragment teacherFragment;
    UserIdentificationFragment othersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_identification);

        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        studentFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.student_fragment);
        teacherFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.teacher_fragment);
        othersFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.others_fragment);

        studentFragment.setBackgroundImage(R.drawable.clownfish);
        studentFragment.setUserTypeText(R.string.i_am_a_student);
        studentFragment.setDesciption(R.string.i_am_a_student_description);

        teacherFragment.setBackgroundImage(R.drawable.jellyfish);
        teacherFragment.setUserTypeText(R.string.i_am_a_teacher);
        teacherFragment.setDesciption(R.string.i_am_a_teacher_description);

        othersFragment.setBackgroundImage(R.drawable.turtle);
        othersFragment.setUserTypeText(R.string.i_want_to);
        othersFragment.setDesciption(R.string.i_want_to_description);

    }
}
