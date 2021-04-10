package com.consultation.studenthelp;

import android.app.Application;


public class StudentApp extends Application {
    private static StudentApp instance;

    public static StudentApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
