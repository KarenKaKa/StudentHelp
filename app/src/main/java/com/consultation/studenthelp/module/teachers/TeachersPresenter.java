package com.consultation.studenthelp.module.teachers;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.LabelsInfo;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
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
                AVQuery<AVObject> labelsQuery = new AVQuery<>(LabelsInfo.TABLE_NAME);
                labelsQuery.findInBackground().subscribe(new Observer<List<AVObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<AVObject> avObjects) {
                        mRootView.setData(students, avObjects);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRootView.setData(students, null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }
}
