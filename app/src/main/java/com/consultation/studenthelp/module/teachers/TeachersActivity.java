package com.consultation.studenthelp.module.teachers;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityTeachersBinding;

/**
 * 咨询师列表
 */
public class TeachersActivity extends BaseActivity<TeachersPresenter> implements TeachersView {
    private ActivityTeachersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachers);
        binding.setLifecycleOwner(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
//        binding.recyclerTeacher.setAdapter();
    }

    @Override
    protected TeachersPresenter createPresenter() {
        return new TeachersPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
