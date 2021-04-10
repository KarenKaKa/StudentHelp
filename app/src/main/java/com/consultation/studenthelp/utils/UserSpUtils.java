package com.consultation.studenthelp.utils;

public class UserSpUtils {
    private static final String IS_LOGIN = "is_login";
    private static final String USER_NAME = "user_name";
    private static final String USER_TYPE = "user_type";//1学生 2老师

    public static void setIsLogin(boolean isLogin) {
        SpUtils.putValue(IS_LOGIN, isLogin);
    }

    public static boolean isLogin() {
        return (boolean) SpUtils.getValue(IS_LOGIN, false);
    }

    public static void setUserType(String userType) {
        SpUtils.putValue(USER_TYPE, userType);
    }

    public static String getUserType() {
        return (String) SpUtils.getValue(USER_TYPE, "1");
    }

    public static void setUserName(String name) {
        SpUtils.putValue(USER_NAME, name);
    }

    public static String getUserName() {
        return (String) SpUtils.getValue(USER_NAME, "");
    }

}
