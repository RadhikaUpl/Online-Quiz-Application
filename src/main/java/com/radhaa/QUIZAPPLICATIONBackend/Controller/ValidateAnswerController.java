package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.DTO.validateAnswer;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.QuestionTable;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Service.ValidateAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/validateAns")
public class ValidateAnswerController
{
    @Autowired
    ValidateAnswerService validateAnswerService;
    @Autowired
    userRepo userrepo;
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<validateAnswer> validateAnswerList, @RequestParam int quizid)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<userTable> user = userrepo.findByUsername(name);
        int userid = user.get().getSignInid();
        return validateAnswerService.checkAnswer(validateAnswerList,userid,quizid);
    }

    @GetMapping("/wrongAns")
    public ResponseEntity<List<QuestionTable>> getWrongAnswer()
    {
        return validateAnswerService.wrongAnswers();
    }
}
