package com.consultation.studenthelp.module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.consultation.studenthelp.CustomUserProvider;
import com.consultation.studenthelp.R;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.module.teacher.TeacherMainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import cn.leancloud.AVUser;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.AVIMOptions;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UserSpUtils.isLogin()) {
            initIm();
            if (UserSpUtils.getUserType().equals("1")) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, TeacherMainActivity.class));
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
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