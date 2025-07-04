package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CardDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardid;
    @NonNull
    private String quizTitle;
    @NonNull
    private int price;
    @NonNull
    private String quizDescription ;
    @NonNull
    private String imgUrl;



    public CardTable(@NonNull String quizTitle, int price, @NonNull String quizDescription, @NonNull String imgUrl) {
        this.quizTitle = quizTitle;
        this.price = price;
        this.quizDescription = quizDescription;
        this.imgUrl = imgUrl;
    }

    @NonNull
    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(@NonNull String quizTitle) {
        this.quizTitle = quizTitle;
    }

    @NonNull
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(@NonNull String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @NonNull
    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(@NonNull String quizDescription) {
        this.quizDescription = quizDescription;
    }

    @OneToMany(mappedBy = "card" ,cascade = CascadeType.ALL )
    @JsonManagedReference(value="cardTable")
    @JsonIgnore
    List<UserQuizPurchase> list=new ArrayList<>();



    @OneToMany(mappedBy = "cardTable",cascade=CascadeType.ALL)
    @JsonManagedReference(value="card_score")
    @JsonIgnore
    List<ScoreStoringEntity> Scorelist= new ArrayList<>();
}
