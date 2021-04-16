package com.consultation.studenthelp.net.vo;

/**
 * 问题列表
 */
public class QuestionsBean {
    private String questionId;//问题id
    private String questionnaireId;//问卷id
    private String questionTitle;//问题描述

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
