package com.radhaa.QUIZAPPLICATIONBackend.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Questions")
public class QuestionTable
{
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int QuestionId;

    @Column(nullable = false)
    private String question;

    private String questionType;

    public QuestionTable() {
    }

    public QuestionTable(@NonNull String question, @NonNull String questionType) {
        this.question = question;
        this.questionType = questionType;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    @NonNull
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(@NonNull String questionType) {
        this.questionType = questionType;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    @OneToMany(mappedBy = "question" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OptionTable>  optList=new ArrayList<>();

    public List<OptionTable> getOptList() {
        return optList;
    }

    public void setOptList(List<OptionTable> Optlist) {
        this.optList = Optlist;
    }
}
