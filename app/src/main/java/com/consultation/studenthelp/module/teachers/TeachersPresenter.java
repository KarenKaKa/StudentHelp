package com.consultation.studenthelp.module.teachers;

import android.util.Log;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TeachersPresenter extends BasePresenter<TeachersView> {
    public TeachersPresenter(TeachersView view) {
        attachView(view);
    }


    public void getTeachers() {
        AVQuery<AVObject> query = new AVQuery<>("Teachers");
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> students) {
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
