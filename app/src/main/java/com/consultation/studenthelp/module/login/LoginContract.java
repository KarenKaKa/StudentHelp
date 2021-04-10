package com.consultation.studenthelp.module.login;

import com.consultation.studenthelp.base.BaseView;

public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();
    }

    interface Model {
//        Observable<List<User>> getUsers(int lastIdQueried, boolean update);
    }
}
