package com.consultation.studenthelp.module.teachers;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.List;

import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TeachersPresenter extends BasePresenter<TeachersView> {
    public TeachersPresenter(TeachersView view) {
        attachView(view);
    }


    public void getTeachers() {
        AVQuery<UserBean> query = new AVQuery<>("Teachers");
        query.findInBackground().subscribe(new Observer<List<UserBean>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<UserBean> students) {
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
