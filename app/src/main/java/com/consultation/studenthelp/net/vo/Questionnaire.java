package com.consultation.studenthelp.net.vo;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷列表
 */
public class Questionnaire extends LitePalSupport implements Serializable {
    private long id;
    private String questionnaireTitle;//问卷描述  抑郁自评量表（SDS）
    private String questionnaireSubTitle;//您好，这是一份抑郁情况调查表。请您仔细阅读每一道题的描述，并根据您最近1周的实际情况，考虑你出现题目所描述情绪的频率，并在所列的答案中选择一个最适合的选项。
    private String scoringCriteria;//评分标准
    private List<Question> questionList;

    public Questionnaire(String questionnaireTitle, String questionnaireSubTitle, String scoringCriteria, List<Question> questionList) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireSubTitle = questionnaireSubTitle;
        this.scoringCriteria = scoringCriteria;
        this.questionList = questionList;
    }

    public Questionnaire(String questionnaireTitle, String questionnaireSubTitle, String scoringCriteria) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireSubTitle = questionnaireSubTitle;
        this.scoringCriteria = scoringCriteria;
    }

    public String getScoringCriteria() {
        return scoringCriteria;
    }

    public void setScoringCriteria(String scoringCriteria) {
        this.scoringCriteria = scoringCriteria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionnaireTitle() {
        return questionnaireTitle;
    }

    public void setQuestionnaireTitle(String questionnaireTitle) {
        this.questionnaireTitle = questionnaireTitle;
    }

    public String getQuestionnaireSubTitle() {
        return questionnaireSubTitle;
    }

    public void setQuestionnaireSubTitle(String questionnaireSubTitle) {
        this.questionnaireSubTitle = questionnaireSubTitle;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
