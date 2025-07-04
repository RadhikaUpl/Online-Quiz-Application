package com.radhaa.QUIZAPPLICATIONBackend.Service;
import com.radhaa.QUIZAPPLICATIONBackend.DTO.UserPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.CardTable;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.UserQuizPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.UserQuizPurchaseRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.cardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class QuizPurchaseService {

    @Autowired
    userRepo userRepo;
    @Autowired
    cardRepo cardRepo;
    @Autowired
    UserQuizPurchaseRepo userQuizPurchaseRepo;


    public ResponseEntity<String> saveData(int userid,UserPurchase userPurchase)
    {
        Optional<userTable> user = userRepo.findById(userid);
        Optional<CardTable> card = cardRepo.findById(userPurchase.getCardId());
        if(user.isPresent() && card.isPresent())
        {
            UserQuizPurchase userQuizPurchase=new UserQuizPurchase();
            userQuizPurchase.setUser(user.get());
            userQuizPurchase.setCard(card.get());
            userQuizPurchase.setPurchase_time(LocalDateTime.now());
            userQuizPurchase.setPaymentStatus(userPurchase.getPaymentStatus());

            userQuizPurchaseRepo.save(userQuizPurchase);
            return ResponseEntity.ok("saved successfully");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("error in saving data");
    }

    public ResponseEntity<?> checkPurchaseForUser(userTable user)
    {
        ArrayList<Integer> cardIdList=new ArrayList<>();

        List<UserQuizPurchase> quizpurchase = userQuizPurchaseRepo.findByUser(user);
        for(UserQuizPurchase quiz : quizpurchase)
        {
            int cardid = quiz.getCard().getCardid();
            cardIdList.add(cardid);
        }
        return ResponseEntity.ok(cardIdList);
    }
}
