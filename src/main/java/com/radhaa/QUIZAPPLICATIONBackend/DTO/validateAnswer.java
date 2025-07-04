package com.radhaa.QUIZAPPLICATIONBackend.DTO;

public class validateAnswer
{
    private int queId;

    private String answer;

    public validateAnswer() {
    }

    public validateAnswer(int queId, String answer) {
        this.queId = queId;
        this.answer = answer;
    }

    public int getQueId() {
        return queId;
    }

    public void setQueId(int queId) {
        this.queId = queId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
