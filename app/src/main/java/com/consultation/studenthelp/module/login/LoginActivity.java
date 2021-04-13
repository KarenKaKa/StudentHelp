package com.consultation.studenthelp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityLoginBinding;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.module.teacher.TeacherMainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    private String userType = "1";
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);

        initView();
    }

    private void initView() {
        binding.ivBack.setOnClickListener(this);
        binding.etInvitateCode.setOnClickListener(this);
        binding.tvGetCode.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        binding.etInput.setText(UserSpUtils.getUserName());
        if (UserSpUtils.getUserType().equals("1")) {
            binding.rbStudent.setChecked(true);
            userType = "1";
        } else {
            binding.rbTeacher.setChecked(true);
            userType = "2";
        }
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbTeacher) {
                    userType = "2";
                } else {
                    userType = "1";
                }
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.tvGetCode) {
            mPresenter.getCode(binding.etInput.getText().toString().trim());
        } else if (id == R.id.btnSubmit) {
            String userName = binding.etInput.getText().toString().trim();
            String code = binding.etInvitateCode.getText().toString().trim();
            //TODO 测试环境 验证码写死
            mPresenter.verifySMSCode(userName, "323215", userType);
        }
    }


    @Override
    public void loginSuccess() {
        if (userType.equals("1")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
        }
        finish();
    }
}