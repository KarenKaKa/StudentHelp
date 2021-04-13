package com.consultation.studenthelp.module.teachers;


import com.consultation.studenthelp.base.BaseView;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;

public interface TeachersView extends BaseView {

    void setData(List<AVUser> students, List<AVObject> avObjects);
}
