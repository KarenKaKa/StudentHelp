package com.consultation.studenthelp.module.login;

import android.text.TextUtils;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.List;

import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter<LoginContract.View> {
    public LoginPresenter(LoginContract.View view) {
        attachView(view);
    }

    public void login(String userName, String code, String userType) {
        if (TextUtils.isEmpty(userName)) {
            mRootView.toast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mRootView.toast("请输入密码");
            return;
        }
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo(UserInfo.USER_NAME, userName);
        query.whereEqualTo(UserInfo.USER_TYPE, userType);
        query.findInBackground().subscribe(new Observer<List<AVUser>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVUser> avUsers) {
                if (avUsers.size() > 0) {
                    AVUser.logIn(userName, code).subscribe(new Observer<AVUser>() {
                        public void onSubscribe(Disposable disposable) {
                        }

                        public void onNext(AVUser user) {
                            // 登录成功
                            UserSpUtils.setIsLogin(true);
                            UserSpUtils.setUserType(userType);
                            UserSpUtils.setUserName(userName);
                            UserSpUtils.setUerId(user.getObjectId());
                            mRootView.loginSuccess();
                        }

                        public void onError(Throwable throwable) {
                            mRootView.toast("密码错误");
                        }

                        public void onComplete() {
                        }
                    });
                } else {
                    mRootView.toast("用户不存在");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRootView.toast("用户不存在");
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void register(String userName, String code, String userType) {
        if (TextUtils.isEmpty(userName)) {
            mRootView.toast("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mRootView.toast("请输入密码");
            return;
        }
        AVUser user = new AVUser();
        user.setUsername(userName);
        user.setPassword(code);
        user.put(UserInfo.USER_TYPE, userType);
        user.put(UserInfo.USER_SKILLS, "");
        user.put(UserInfo.USER_LABELS, "");
        user.signUpInBackground().subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(AVUser user) {
                login(userName, code, userType);
            }

            public void onError(Throwable throwable) {
                // 注册失败（通常是因为用户名已被使用）
                mRootView.toast("账户已存在，请登录");
            }

            public void onComplete() {
            }
        });
    }
}
