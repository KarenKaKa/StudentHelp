package com.consultation.studenthelp.net.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题列表
 */
public class QuestionsBean {
    private String questionId;//问题id
    private String questionnaireId;//问卷id
    private String questionTitle;//问题描述
    private List<AnswersBean> answers;
    private boolean isMuti = false;

    private List<AnswersBean> selectedList = new ArrayList<>();

    public List<AnswersBean> getSelectedList() {
        return selectedList;
    }

    public boolean isMuti() {
        return isMuti;
    }

    public void setMuti(boolean muti) {
        isMuti = muti;
    }

    public QuestionsBean(List<AnswersBean> answers,boolean isMuti) {
        this.answers = answers;
        this.isMuti = isMuti;
    }

    public List<AnswersBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersBean> answers) {
        this.answers = answers;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
}
