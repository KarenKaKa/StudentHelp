package com.consultation.studenthelp;

import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为 fake，仅供参考
    static {
        AVQuery<AVUser> query = AVUser.getQuery();
        query.findInBackground().subscribe(new Observer<List<AVUser>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVUser> teachers) {
                for (AVUser teacher : teachers) {
                    partUsers.add(new LCChatKitUser(teacher.getObjectId(), teacher.getUsername(), ""));
                }
            }

            public void onError(Throwable throwable) {
            }

            public void onComplete() {
            }
        });
    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    @Override
    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
