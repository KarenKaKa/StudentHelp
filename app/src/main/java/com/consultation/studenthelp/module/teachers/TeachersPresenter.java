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


    public void getTeachers(String selectedLabelId) {
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo(UserInfo.USER_TYPE, UserInfo.USER_TYPE_TEACHER);
        if (!selectedLabelId.equals("all")) {
            query.whereContains(UserInfo.USER_LABELS, selectedLabelId);
        }
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

    public void getLabels() {
        AVQuery<AVObject> labelsQuery = new AVQuery<>(LabelsInfo.TABLE_NAME);
        labelsQuery.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVObject> avObjects) {
                mRootView.setLabels(avObjects);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mRootView.toast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
