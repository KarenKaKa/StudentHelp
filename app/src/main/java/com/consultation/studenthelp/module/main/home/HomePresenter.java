package com.consultation.studenthelp.module.main.home;

import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.vo.LabelsInfo;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeView> {
    public HomePresenter(HomeView view) {
        attachView(view);
    }

    public void getSorts() {
        AVQuery<AVObject> query = new AVQuery<>(LabelsInfo.TABLE_NAME);
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
}
