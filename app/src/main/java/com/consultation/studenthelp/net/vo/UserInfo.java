package com.consultation.studenthelp.net.vo;

/**
 * 用户表
 */
public class UserInfo extends BaseTableInfo {
    public static final String TABLE_NAME = "_User";
    public static final String USER_PHONE = "mobilePhoneVerified";
    public static final String USER_TYPE = "type";
    public static final String USER_TYPE_TEACHER = "2";
    public static final String USER_TYPE_STUDENT = "1";
    public static final String USER_NAME = "username";
    public static final String USER_GENDER = "gender";
    public static final String USER_LABELS = "labels";
    public static final String USER_SKILLS = "skills";
    public static final String USER_AVAILABLE = "available"; //是否有空 true按钮咨询 false按钮留言
}
