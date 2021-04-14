package com.consultation.studenthelp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.CustomUserProvider;
import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityLoginBinding;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.module.teacher.TeacherMainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import cn.leancloud.AVUser;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.AVIMOptions;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    private String userType = "1";
    private ActivityLoginBinding binding;
    private boolean isLogin = true;//是否为登录页面  否则为注册

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
        binding.tvForget.setOnClickListener(this);
        binding.tvRegister.setOnClickListener(this);
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
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.btnSubmit) {
            String userName = binding.etInput.getText().toString().trim();
            String code = binding.etInvitateCode.getText().toString().trim();
            if (isLogin) {
                mPresenter.login(userName, code, userType);
            } else {
                mPresenter.register(userName, code, userType);
            }
        } else if (id == R.id.tvForget) {
            toast("请联系管理员修改");
        } else if (id == R.id.tvRegister) {
            if (isLogin) {
                isLogin = false;
                binding.tvTitle.setText("注册");
                binding.btnSubmit.setText("注册");
                binding.tvForget.setVisibility(View.INVISIBLE);
                binding.tvRegister.setText("已有账号，登录");
            } else {
                isLogin = true;
                binding.tvTitle.setText("登录");
                binding.btnSubmit.setText("登录");
                binding.tvForget.setVisibility(View.VISIBLE);
                binding.tvRegister.setText("注册");
            }
        }
    }


    @Override
    public void loginSuccess() {
        initIm();
        if (userType.equals("1")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
        }
        finish();
    }

    private void initIm() {
        if (!UserSpUtils.getUserName().isEmpty()) {
            LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
            AVIMOptions.getGlobalOptions().setAutoOpen(true);
            LCChatKit.getInstance().open(AVUser.getCurrentUser().getObjectId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null == e) {
                        Log.e("arms", "Im初始化了成功了");
                    } else {
                        Log.e("arms", "Im初始化了失败了" + e.toString());
                    }

                }
            });
        }
    }
}