package com.consultation.studenthelp.module.teachers;


import com.consultation.studenthelp.base.BaseView;
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.List;

import cn.leancloud.AVObject;

public interface TeachersView extends BaseView {

    void setData(List<AVObject> students);
}
