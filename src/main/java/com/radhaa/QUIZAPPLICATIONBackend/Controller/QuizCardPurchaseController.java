package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Service.QuizPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;

import java.util.Optional;

@RestController
@RequestMapping("/quizPurchase")
public class QuizCardPurchaseController
{
    @Autowired
    userRepo userRepo;
    @Autowired
    QuizPurchaseService quizPurchaseService;
    @GetMapping("/purchase")
    public ResponseEntity<?> checkCardsforUser()
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<userTable> byUsername = userRepo.findByUsername(name);
        if(byUsername.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");
        }
        userTable user=byUsername.get();
        return quizPurchaseService.checkPurchaseForUser(user);
    }
}
