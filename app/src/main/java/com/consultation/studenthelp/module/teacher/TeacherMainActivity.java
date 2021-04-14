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
import com.consultation.studenthelp.module.userinfo.EditUserInfoActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

//TODO 待做个人信息编辑页  性别 昵称 职称 擅长分类
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
        binding.tvMessage.setOnClickListener(this);
        binding.tvArt.setOnClickListener(this);

        binding.name.setText(UserSpUtils.getUserName());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivHead) {
            startActivity(new Intent(this, EditUserInfoActivity.class));
        } else if (id == R.id.tvConsults) {
            toast("咨询列表");
        } else if (id == R.id.tvMessage) {
            toast("留言列表");
        } else if (id == R.id.tvAppointment) {
            toast("预约列表");
        } else if (id == R.id.tvTests) {
            toast("发布测试");
        } else if (id == R.id.tvArt) {
            toast("文章列表");
//            startActivity(new Intent(this, PublishActivity.class));
        } else if (id == R.id.tvLogout) {
            UserSpUtils.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}