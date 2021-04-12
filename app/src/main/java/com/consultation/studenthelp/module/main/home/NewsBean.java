package com.consultation.studenthelp.module.main.home;

public class NewsBean {
    private String title;
    private String teacherId;
    private String teacherName;
    private String time;

    public NewsBean(String title, String teacherName) {
        this.title = title;
        this.teacherName = teacherName;
    }

    public String getTitle() {
        return title;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTime() {
        return time;
    }
}
