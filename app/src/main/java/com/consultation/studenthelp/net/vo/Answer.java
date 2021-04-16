package com.consultation.studenthelp.net.vo;

import org.litepal.crud.LitePalSupport;

/**
 * 答案列表
 */
public class Answer extends LitePalSupport {
    private long id;
    private String answerTitle;//答案描述
    private int score = 0;//答案分值
    private Question question;

    public Answer(String answerTitle, int score) {
        this.answerTitle = answerTitle;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
