package com.radhaa.QUIZAPPLICATIONBackend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreStoringEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int score;

    @ManyToOne
    @JsonBackReference(value="user_score")
    @JoinColumn(name="userId")
    userTable userTable;

    @ManyToOne
    @JsonBackReference(value="card_score")
    @JoinColumn(name="cardId")
    CardTable cardTable;

}
