package com.consultation.studenthelp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.module.main.MainActivity;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    private ImageView ivBack;
    private EditText etInput;
    private EditText etInvitateCode;
    private TextView tvGetCode;
    private TextView btnSubmit;
    private RadioGroup radioGroup;

    private String userType = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        etInput = findViewById(R.id.etInput);
        etInvitateCode = findViewById(R.id.etInvitateCode);
        tvGetCode = findViewById(R.id.tvGetCode);
        btnSubmit = findViewById(R.id.btnSubmit);
        radioGroup = findViewById(R.id.radioGroup);
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
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.etInvitateCode:
                break;
            case R.id.tvGetCode:
                break;
            case R.id.btnSubmit:
                String userName = etInput.getText().toString().trim();
                String code = etInvitateCode.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    toast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    toast("请输入验证码");
                    return;
                }
                mPresenter.login(userType, userName, code);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}