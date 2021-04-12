package com.consultation.studenthelp.module.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityTeacherMainBinding;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

public class TeacherMainActivity extends BaseActivity implements View.OnClickListener {
    private ActivityTeacherMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_main);
        binding.setLifecycleOwner(this);

        binding.ivHead.setOnClickListener(this);
        binding.tvConsults.setOnClickListener(this);
        binding.tvAppointment.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
        binding.tvTests.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivHead) {
            toast("修改头像");
        } else if (id == R.id.tvConsults) {
            toast("咨询列表");
        } else if (id == R.id.tvAppointment) {
            toast("预约列表");
        } else if (id == R.id.tvTests) {
            toast("发布测试");
        } else if (id == R.id.tvLogout) {
            UserSpUtils.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}