package com.consultation.studenthelp.base;

import com.consultation.studenthelp.net.ApiFactory;
import com.consultation.studenthelp.net.ApiService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class BasePresenter<V> {
    public V mRootView;
    protected ApiService apiService;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V mRootView) {
        this.mRootView = mRootView;
        apiService = ApiFactory.retrofit().create(ApiService.class);
    }

    public void detachView() {
        this.mRootView = null;
        onUnSubscribe();
    }

    //RxJava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }


    public void addSubscription(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
