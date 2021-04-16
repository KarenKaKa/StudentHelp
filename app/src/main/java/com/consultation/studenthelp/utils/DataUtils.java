package com.consultation.studenthelp.utils;

import com.consultation.studenthelp.net.vo.Question;
import com.consultation.studenthelp.net.vo.Questionnaire;

import org.litepal.LitePal;

import java.util.List;

public class DataUtils {
    //获取问卷的方法
    public static List<Questionnaire> getQuestionnaireList() {
        return LitePal.findAll(Questionnaire.class);
    }
    //获取问题的方法
    public static List<Question> getQuestionList(long questionnaireId) {
        return LitePal.where("questionnaire_id = ?", questionnaireId + "").find(Question.class,true);
    }

}
