package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.CardTable;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.OptionTable;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.QuestionTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.cardRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.questionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService
{
    @Autowired
    questionRepo questionRepo;
    @Autowired
    cardRepo cardRepo;
    public ResponseEntity<String> addQue(QuestionTable questionTable)
    {
        if(questionTable.getOptList()!=null)
        {
            for(OptionTable options:questionTable.getOptList())
            {
                options.setQuestion(questionTable);
            }
        }
        questionRepo.save(questionTable);
        return ResponseEntity.ok("question added ");
    }

    public List<QuestionTable> getQue(String type)
    {
        List<QuestionTable> list=questionRepo.findByQuestionType(type);
        Collections.shuffle(list);
        return list;
    }

    public ResponseEntity<String> deleteQue(int id)
    {
        Optional<QuestionTable> questionTable=questionRepo.findById(id);
        if(questionTable.isPresent())
        {
            questionRepo.deleteById(id);
            return ResponseEntity.ok("question deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("question not found");
    }

    public ResponseEntity<String> addCards(CardTable card)
    {
        cardRepo.save(card);
        return ResponseEntity.ok("saved successfully");
    }
    public ResponseEntity<List<CardTable>> getAllCards()
    {
        List<CardTable> list=cardRepo.findAll();
       return ResponseEntity.ok(list);
    }


}
