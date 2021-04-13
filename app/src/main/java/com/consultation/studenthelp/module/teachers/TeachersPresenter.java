package com.consultation.studenthelp.module.teachers;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TeachersPresenter extends BasePresenter<TeachersView> {
    public TeachersPresenter(TeachersView view) {
        attachView(view);
    }


    public void getTeachers() {
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo(UserInfo.USER_TYPE, UserInfo.USER_TYPE_TEACHER);
        query.findInBackground().subscribe(new Observer<List<AVUser>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVUser> students) {
                mRootView.setData(students);
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }
}
