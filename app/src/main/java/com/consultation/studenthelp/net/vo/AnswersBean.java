package com.consultation.studenthelp.net.vo;

/**
 * 答案列表
 */
public class AnswersBean {
    private String answerTitle;//答案描述
    private String questionId;//问题id
    private int score = 0;//答案分值

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
