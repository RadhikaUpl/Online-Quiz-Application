package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name="user_quiz_purchase")
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserQuizPurchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String paymentStatus;

    private LocalDateTime purchase_time;

    public UserQuizPurchase(String paymentStatus, LocalDateTime purchase_time) {
        this.paymentStatus = paymentStatus;
        this.purchase_time = purchase_time;
    }

    @ManyToOne()
    @JoinColumn(name="userId")
    @JsonBackReference(value="userTable")
    userTable user;

    public userTable getUser() {
        return user;
    }

    public void setUser(userTable user) {
        this.user = user;
    }

    @ManyToOne()
    @JoinColumn(name="quizCardId")
    @JsonBackReference(value="cardTable")
    CardTable card;

    public CardTable getCard() {
        return card;
    }

    public void setCard(CardTable card) {
        this.card = card;
    }
}
