package com.consultation.studenthelp.net.vo;


public class TeacherBean {
    private String name;
    private String goodAt;

    public TeacherBean(String name, String goodAt) {
        this.name = name;
        this.goodAt = goodAt;
    }

    public String getName() {
        return name;
    }

    public String getGoodAt() {
        return goodAt;
    }
}
