package com.consultation.studenthelp.module.login;

import android.text.TextUtils;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import cn.leancloud.sms.AVSMS;
import cn.leancloud.sms.AVSMSOption;
import cn.leancloud.types.AVNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter extends BasePresenter<LoginContract.View> {
    public LoginPresenter(LoginContract.View view) {
        attachView(view);
    }

    private String objectId = "";

    public void getCode(String userName) {
        if (TextUtils.isEmpty(userName)) {
            mRootView.toast("请输入手机号");
            return;
        }
        isRegisted(userName);
    }

    public void isRegisted(String userName) {
        AVQuery<AVObject> query = new AVQuery<>(UserInfo.TABLE_NAME);
        query.whereEqualTo(UserInfo.USER_PHONE, userName);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> users) {
                if (users.size() > 0) {
                    objectId = users.get(0).getObjectId();
                    AVUser.requestLoginSmsCodeInBackground(userName).subscribe(new Observer<AVNull>() {
                        public void onSubscribe(Disposable disposable) {
                        }

                        public void onNext(AVNull avNull) {
                            mRootView.toast("验证码发送成功");
                        }

                        public void onError(Throwable throwable) {
                            mRootView.toast("验证码发送失败：" + throwable.getMessage());
                        }

                        public void onComplete() {
                        }
                    });
                } else {
                    AVSMSOption option = new AVSMSOption();
                    option.setTtl(10);//验证码有效期10分钟
                    option.setApplicationName("大学生心理咨询");
                    option.setOperation("登录注册");
                    AVSMS.requestSMSCodeInBackground(userName, option).subscribe(new Observer<AVNull>() {
                        @Override
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override
                        public void onNext(AVNull avNull) {
                            mRootView.toast("验证码发送成功");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mRootView.toast("验证码发送失败：" + throwable.getMessage());
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
                }
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }


    public void verifySMSCode(String userName, String code, String userType) {
        if (TextUtils.isEmpty(userName)) {
            mRootView.toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mRootView.toast("请输入验证码");
            return;
        }
        AVUser user = new AVUser();
        user.setUsername(userName);
        user.setPassword("123456");
        user.setMobilePhoneNumber(userName);
        AVUser.signUpOrLoginByMobilePhoneInBackground(userName, code).subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(AVUser user) {
                saveUser(userName, userType);
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }

    private void saveUser(String userName, String userType) {
        AVObject user = TextUtils.isEmpty(objectId) ? new AVObject(UserInfo.TABLE_NAME) : AVObject.createWithoutData(UserInfo.TABLE_NAME, objectId);
        user.put(UserInfo.USER_NAME, userName);
        user.put(UserInfo.USER_PHONE, userName);
        user.put(UserInfo.USER_TYPE, userType);
        user.saveInBackground().subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(AVObject user) {
                UserSpUtils.setIsLogin(true);
                UserSpUtils.setUserType(userType);
                UserSpUtils.setUserName(userName);
                mRootView.loginSuccess();
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }
}
