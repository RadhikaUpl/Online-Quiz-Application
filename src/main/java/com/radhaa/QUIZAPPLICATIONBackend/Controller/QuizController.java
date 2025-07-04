package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.CardTable;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.QuestionTable;
import com.radhaa.QUIZAPPLICATIONBackend.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController
{
    @Autowired
    QuizService quizService;

    @PostMapping("/add")
    public ResponseEntity<String> addQue(@RequestBody QuestionTable questions)
    {
        return quizService.addQue(questions);
    }

    @GetMapping("/{questionType}")
    public ResponseEntity<List<QuestionTable> > getAllQuestion(@PathVariable String questionType)
    {
        List<QuestionTable> list=  quizService.getQue(questionType);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> deleteQue(@PathVariable int id)
    {
        return quizService.deleteQue(id);
    }

    @GetMapping("/getCards")
    public ResponseEntity<List<CardTable>> getAllCards()
    {
        return quizService.getAllCards();
    }
    @PostMapping("/addCards")
    public ResponseEntity<String> addCards(@RequestBody CardTable card)
    {
        return quizService.addCards(card);
    }

}
