package com.consultation.studenthelp.module;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UserSpUtils.isLogin()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}