package com.consultation.studenthelp.module.login;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.utils.UserSpUtils;

public class LoginPresenter extends BasePresenter<LoginContract.View> {
    public LoginPresenter(LoginContract.View view) {
        attachView(view);
    }

    public void login(String userType, String userName, String code) {
        UserSpUtils.setIsLogin(true);
        UserSpUtils.setUserType(userType);
        UserSpUtils.setUserName(userName);
        mRootView.loginSuccess();
    }
}
