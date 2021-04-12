package com.consultation.studenthelp;

import android.app.Application;

import com.consultation.studenthelp.utils.SpUtils;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;


public class StudentApp extends Application {
    private static StudentApp instance;

    public static StudentApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SpUtils.getInstance(this, "spConfig");

        AVOSCloud.initialize(this, "EbbwkkutKs8hiC6dYN1Kk5kQ-9Nh9j0Va", "X1TgNrdGmBhhBzxzpTq3bL40", "https://ebbwkkut.lc-cn-e1-shared.com");
        // 在 AVOSCloud.initialize() 之前调用
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    }
}
