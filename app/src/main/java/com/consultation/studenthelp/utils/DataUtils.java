package com.consultation.studenthelp.utils;

import com.consultation.studenthelp.net.vo.Question;
import com.consultation.studenthelp.net.vo.Questionnaire;

import org.litepal.LitePal;

import java.util.List;

public class DataUtils {
    public static List<Questionnaire> getQuestionnaireList() {
        return LitePal.findAll(Questionnaire.class);
    }

    public static List<Question> getQuestionList(long id) {
        return LitePal.where("questionnaire_id = ?", id + "").find(Question.class,true);
    }

}
