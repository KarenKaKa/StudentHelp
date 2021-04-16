package com.consultation.studenthelp.net.vo;

import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * 问题列表
 */
public class Question extends LitePalSupport {
    private long id;
    private String questionTitle;//问题描述
    private Questionnaire questionnaire;
    private boolean multiSelect;
    private List<Answer> answerList;

    public Question(String questionTitle, boolean multiSelect, List<Answer> answerList) {
        this.questionTitle = questionTitle;
        this.multiSelect = multiSelect;
        this.answerList = answerList;
    }

    public Question(String questionTitle, boolean multiSelect) {
        this.questionTitle = questionTitle;
        this.multiSelect = multiSelect;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }
}
