package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.DTO.validateAnswer;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.*;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.ScoreStoreRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.cardRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.questionRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ValidateAnswerService
{
    @Autowired
   questionRepo questionRepo;
    @Autowired
    ScoreStoreRepo scoreStoreRepo;
    @Autowired
    cardRepo cardrepo;
    @Autowired
    userRepo userrepo;
    ArrayList<Integer> wrongQuestionID=new ArrayList<>();

    public ResponseEntity<Integer> checkAnswer(List<validateAnswer> validatelist,int userid,int quizid)
    {
        int score=0;
        for(validateAnswer Item:validatelist)
        {
            int queId=Item.getQueId();
            Optional<QuestionTable> byId = questionRepo.findById(queId);
            if(byId.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(0);
            }
            String answer=Item.getAnswer();

            List<OptionTable> Optionlist = byId.get().getOptList();
            boolean isCorrect=false;
            for(OptionTable option:Optionlist)
            {
                if(option.getOptions().equals(answer) && option.getIsCorrect())
                {
                    score=score+1;
                    isCorrect=true;
                    break;
                }
            }
            if(!isCorrect)
            {
                wrongQuestionID.add(queId);
            }

        }
        System.out.println(wrongQuestionID);
//        String finalScore=String.valueOf(score);

        Optional<userTable> user = userrepo.findById(userid);
        Optional<CardTable> card = cardrepo.findById(quizid);
        if(user.isPresent() && card.isPresent())
        {
            userTable userObj=user.get();
            CardTable cardobj=card.get();

            scoreStoreRepo.save(
                    ScoreStoringEntity.builder().score(score)
                   .userTable(userObj)
                   .cardTable(cardobj)
                            .build()
            );
        }

        return ResponseEntity.ok(score);
    }

    public ResponseEntity<List<QuestionTable>> wrongAnswers()
    {
        ArrayList<QuestionTable> list=new ArrayList<>();
        for(int id:wrongQuestionID)
        {
            Optional<QuestionTable> byId = questionRepo.findById(id);
            if(byId.isPresent())
            {
                QuestionTable questionTable = byId.get();
                list.add(questionTable);
            }
        }
        wrongQuestionID.clear();
        return ResponseEntity.ok(list);
    }
}
