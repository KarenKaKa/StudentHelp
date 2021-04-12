package com.consultation.studenthelp.net.vo;


import cn.leancloud.AVObject;

public class UserBean extends AVObject {
    private String name;
    private String gender;
    private String labels;
    private String skills;

    public UserBean(String name,String skills){
        this.name = name;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
