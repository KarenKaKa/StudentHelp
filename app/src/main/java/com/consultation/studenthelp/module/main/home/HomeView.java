package com.consultation.studenthelp.module.main.home;


import com.consultation.studenthelp.base.BaseView;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;

public interface HomeView extends BaseView {

    void setSortData(List<AVObject> labels);

    void setTeachers(List<AVUser> teachers);

    void setArticlesData(List<AVObject> labels);
}
