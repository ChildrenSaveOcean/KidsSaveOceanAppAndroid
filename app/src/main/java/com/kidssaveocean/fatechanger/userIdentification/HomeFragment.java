package com.kidssaveocean.fatechanger.userIdentification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kidssaveocean.fatechanger.R;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {

    UserIdentificationFragment studentFragment;
    UserIdentificationFragment teacherFragment;
    UserIdentificationFragment othersFragment;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        FragmentManager fragmentManager = getChildFragmentManager();
        studentFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.student_fragment);
        teacherFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.teacher_fragment);
        othersFragment = (UserIdentificationFragment) fragmentManager.findFragmentById(R.id.others_fragment);

        studentFragment.setBackgroundImage(R.drawable.clownfish);
        studentFragment.setUserTypeText(R.string.i_am_a_student);
        studentFragment.setDesciption(R.string.i_am_a_student_description);
        studentFragment.setUpImageButtonOnClick(getString(R.string.students));

        teacherFragment.setBackgroundImage(R.drawable.jellyfish);
        teacherFragment.setUserTypeText(R.string.i_am_a_teacher);
        teacherFragment.setDesciption(R.string.i_am_a_teacher_description);
        teacherFragment.setUpImageButtonOnClick(getString(R.string.teachers));

        othersFragment.setBackgroundImage(R.drawable.turtle);
        othersFragment.setUserTypeText(R.string.neither_student_nor_teacher);
        othersFragment.setDesciption(R.string.neither_student_nor_teacher_description);
        othersFragment.setUpImageButtonOnClick(getString(R.string.others));

        return view;
    }
}
