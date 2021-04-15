package com.consultation.studenthelp.module.main.home;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.ArticlesInfo;
import com.consultation.studenthelp.net.vo.LabelsInfo;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeView> {
    public HomePresenter(HomeView view) {
        attachView(view);
    }

    public void getSorts() {
        AVQuery<AVObject> query = new AVQuery<>(LabelsInfo.TABLE_NAME);
        query.limit(8);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVObject> labels) {
                mRootView.setSortData(labels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getTeachers() {
        AVQuery<AVUser> query = AVUser.getQuery();
        query.whereEqualTo(UserInfo.USER_TYPE, UserInfo.USER_TYPE_TEACHER);
        query.limit(5);
        query.findInBackground().subscribe(new Observer<List<AVUser>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVUser> teachers) {
                mRootView.setTeachers(teachers);
            }

            public void onError(Throwable throwable) {
                mRootView.toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }

    public void getArticles() {
        AVQuery<AVObject> query = new AVQuery<>(ArticlesInfo.TABLE_NAME);
        query.setLimit(10);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVObject> labels) {
                mRootView.setArticlesData(labels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
