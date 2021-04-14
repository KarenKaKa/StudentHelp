package com.consultation.studenthelp;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

  private static CustomUserProvider customUserProvider;

  public synchronized static CustomUserProvider getInstance(List<AVObject> result) {
    if (!result.isEmpty()) {
      partUsers.clear();
      for (AVObject avObject : result) {
        partUsers.add(new LCChatKitUser(avObject.getObjectId(),avObject.getString("username"),""));
      }

    }
    if (null == customUserProvider) {
      customUserProvider = new CustomUserProvider();
    }
    return customUserProvider;
  }

  private CustomUserProvider() {
  }

  private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

  // 此数据均为 fake，仅供参考

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
