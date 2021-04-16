package com.consultation.studenthelp;

import android.app.Application;

import com.consultation.studenthelp.module.main.mine.MyConversationListActivity;
import com.consultation.studenthelp.net.vo.DataListProvider;
import com.consultation.studenthelp.utils.SpUtils;

import org.litepal.LitePal;

import cn.leancloud.AVInstallation;
import cn.leancloud.AVLogger;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.AVIMOptions;
import cn.leancloud.push.PushService;
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
        LitePal.initialize(this);
        initDb();
        SpUtils.getInstance(this, "spConfig");

        // 在 AVOSCloud.initialize() 之前调用
        AVOSCloud.setLogLevel(AVLogger.Level.DEBUG);
        AVIMOptions.getGlobalOptions().setDisableAutoLogin4Push(true);

        AVOSCloud.initialize(this, "EbbwkkutKs8hiC6dYN1Kk5kQ-9Nh9j0Va", "X1TgNrdGmBhhBzxzpTq3bL40", "https://ebbwkkut.lc-cn-e1-shared.com");
        LCChatKit.getInstance().init(this, "EbbwkkutKs8hiC6dYN1Kk5kQ-9Nh9j0Va", "X1TgNrdGmBhhBzxzpTq3bL40", "https://ebbwkkut.lc-cn-e1-shared.com");

        PushService.setDefaultPushCallback(this, MyConversationListActivity.class);
        PushService.setAutoWakeUp(true);
        PushService.setDefaultChannelId(this, "default");

        AVInstallation.getCurrentInstallation().saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AVObject avObject) {
                String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                System.out.println("---  " + installationId);
            }

            @Override
            public void onError(Throwable e) {
                // 保存失败，输出错误信息
                System.out.println("failed to save installation.");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initDb() {
        DataListProvider.init();
    }
}
