package com.consultation.studenthelp.net.bean;

import cn.leancloud.AVObject;

public class Artical extends AVObject {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String title;
    String content;

    @Override
    public String toString() {
        return "Artical{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
