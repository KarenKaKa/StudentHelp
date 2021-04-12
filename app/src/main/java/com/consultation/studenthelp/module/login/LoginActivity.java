package com.consultation.studenthelp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityLoginBinding;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.module.teacher.TeacherMainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import cn.leancloud.sms.AVSMS;
import cn.leancloud.sms.AVSMSOption;
import cn.leancloud.types.AVNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
            getCode();
        } else if (id == R.id.btnSubmit) {
            String userName = binding.etInput.getText().toString().trim();
            String code = binding.etInvitateCode.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                toast("请输入手机号");
                return;
            }
            if (TextUtils.isEmpty(code)) {
                toast("请输入验证码");
                return;
            }
            AVSMS.verifySMSCodeInBackground(code, userName).subscribe(new Observer<AVNull>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(AVNull avNull) {
                    UserSpUtils.setIsLogin(true);
                    UserSpUtils.setUserType(userType);
                    UserSpUtils.setUserName(userName);
                    if (userType.equals("1")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
                    }
                    finish();
                }

                @Override
                public void onError(Throwable throwable) {
                    toast(throwable.getMessage());
                    //TODO 审核通过之后删除
                    UserSpUtils.setIsLogin(true);
                    UserSpUtils.setUserType(userType);
                    UserSpUtils.setUserName(userName);
                    if (userType.equals("1")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
                    }
                    finish();
                }

                @Override
                public void onComplete() {
                }
            });
        }
    }

    private void getCode() {
        String userName = binding.etInput.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            toast("请输入手机号");
            return;
        }

        AVSMSOption option = new AVSMSOption();
        option.setTtl(6);
        option.setApplicationName("大学生心理咨询");
        option.setOperation("登录注册");
        AVSMS.requestSMSCodeInBackground(userName, option).subscribe(new Observer<AVNull>() {
            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(AVNull avNull) {
                toast("短信发送成功");
            }

            @Override
            public void onError(Throwable throwable) {
                toast("短信发送失败：" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
    }


    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}