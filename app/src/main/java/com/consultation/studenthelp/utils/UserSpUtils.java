package com.consultation.studenthelp.utils;

import cn.leancloud.AVUser;

public class UserSpUtils {
    private static final String IS_LOGIN = "is_login";
    private static final String USER_NAME = "user_name";
    private static final String USER_TYPE = "user_type";//1学生 2老师
    private static final String USER_ID = "user_id";//1学生 2老师

    public static void setIsLogin(boolean isLogin) {
        SpUtils.putValue(IS_LOGIN, isLogin);
    }

    public static boolean isLogin() {
        return (boolean) SpUtils.getValue(IS_LOGIN, false);
    }

    public static String getUserType() {
        return (String) SpUtils.getValue(USER_TYPE, "1");
    }

    public static void setUserType(String userType) {
        SpUtils.putValue(USER_TYPE, userType);
    }

    public static String getUserName() {
        return (String) SpUtils.getValue(USER_NAME, "");
    }

    public static void setUserName(String name) {
        SpUtils.putValue(USER_NAME, name);
    }

    public static void setUerId(String userId) {
        SpUtils.putValue(USER_ID, userId);
    }

    public static String getUserId() {
        return (String) SpUtils.getValue(USER_ID, "");
    }

    public static void logout() {
        SpUtils.remove(IS_LOGIN);
        SpUtils.remove(USER_ID);
        AVUser.logOut();
    }

}
