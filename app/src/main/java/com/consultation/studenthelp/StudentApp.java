package com.consultation.studenthelp;

import android.app.Application;
import android.util.Log;

import com.consultation.studenthelp.net.RxSchedulers;
import com.consultation.studenthelp.net.vo.ArticlesInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.SpUtils;

import java.util.List;

import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.v2.AVIMClient;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class StudentApp extends Application {
//    public  AVIMClient client = null;
    private static StudentApp instance;

    public static StudentApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SpUtils.getInstance(this, "spConfig");
        AVOSCloud.initialize(this, "EbbwkkutKs8hiC6dYN1Kk5kQ-9Nh9j0Va", "X1TgNrdGmBhhBzxzpTq3bL40", "https://ebbwkkut.lc-cn-e1-shared.com");
        LCChatKit.getInstance().init(this, "EbbwkkutKs8hiC6dYN1Kk5kQ-9Nh9j0Va", "X1TgNrdGmBhhBzxzpTq3bL40", "https://ebbwkkut.lc-cn-e1-shared.com");
        getAllUsers();
        // 在 AVOSCloud.initialize() 之前调用
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
    }

    private void getAllUsers() {
        AVQuery<AVObject> query = new AVQuery<>(UserInfo.TABLE_NAME);
        query.findInBackground().compose(RxSchedulers.Schedulers()).subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> result) {
                if (!result.isEmpty()) {
                    LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance(result));
                }
            }

            public void onError(Throwable throwable) {
                Log.e(this.getClass().getSimpleName(), throwable.toString());
            }

            public void onComplete() {
            }
        });
    }
}
