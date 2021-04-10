package com.consultation.studenthelp.module.login;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.HashMap;

public class LoginPresenter extends BasePresenter<LoginContract.View> {
    public LoginPresenter(LoginContract.View view) {
        attachView(view);
    }

    public void getUserInfo(HashMap map) {
//        mvpView.showLoading();
//        addSubscription(apiService.userIdentityStatus(map),
//                new ApiCallback<UserInfo>() {
//                    @Override
//                    public void onSuccess(UserInfo model) {
//                        mvpView.setText("成功了");
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        mvpView.setText("失败了");
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        mvpView.hideLoading();
//                    }
//
//                });
    }

    public void login(String userType, String userName, String code) {
        UserSpUtils.setIsLogin(true);
        UserSpUtils.setUserType(userType);
        UserSpUtils.setUserName(userName);
        mRootView.loginSuccess();
    }
}
