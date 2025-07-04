package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name="Options")
public class OptionTable
{


    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optId;
    @Column(nullable = false)
    private String options;
    @Column(nullable = false)
    private boolean isCorrect;

    public OptionTable() {
    }

    public OptionTable(String options, boolean isCorrect) {
        this.options = options;
        this.isCorrect = isCorrect;
    }
    public int getOptId() {
        return optId;
    }

    public void setOptId(int optId) {
        this.optId = optId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }

    @ManyToOne()
    @JoinColumn(name="question_id")
    @JsonBackReference
    private QuestionTable question;

    public QuestionTable getQuestion() {
        return question;
    }

    public void setQuestion(QuestionTable questions) {
        this.question = questions;
    }
}
