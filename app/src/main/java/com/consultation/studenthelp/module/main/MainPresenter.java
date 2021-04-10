package com.consultation.studenthelp.module.main;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.ApiCallback;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.HashMap;

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView view) {
        attachView(view);
    }

    public void getUserInfo(HashMap map) {
        mvpView.showLoading();
        addSubscription(apiService.userIdentityStatus(map),
                new ApiCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo model) {
                        mvpView.setText("成功了");
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.setText("失败了");
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }
}
