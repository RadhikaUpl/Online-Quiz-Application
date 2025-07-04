package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.DTO.ProductRequest;
import com.radhaa.QUIZAPPLICATIONBackend.DTO.UserPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.StripeResponse;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.StripeResponseRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;
import com.stripe.Stripe;


import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StripeService
{
    @Autowired
    userRepo userrepo;
    @Autowired
    StripeResponseRepo stripeResponseRepo;
    @Autowired
    QuizPurchaseService quizPurchaseService;
    //fetch stripe api
    //input to stripe productRequest
    //return sessionId and url

    @Value("${stripe.secretKey}")
    private String secretKey;
    public StripeResponse checkoutProducts(ProductRequest productRequest)
    {
        Stripe.apiKey=secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()//this we are getting from stripe jar
                .setName(productRequest.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount()*100)
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/HomePage/index.html?session_id={CHECKOUT_SESSION_ID}&paymentStatus=SUCCESS")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();

        Session session=null;
        try{
            session = session.create(params);
            StripeResponse stripeResponse=new StripeResponse();
            stripeResponse.setStatus("Success");
            stripeResponse.setMessage("Payment session created");
            stripeResponse.setSessionId(session.getId());
            stripeResponse.setSessionUrl(session.getUrl());

            stripeResponseRepo.save(stripeResponse);
            return stripeResponse;

        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }

        return StripeResponse.builder()
                .status("Failed")
                .message("failed to create payment session")
                .build();
    }

      public ResponseEntity<String> checkSessionId(int userid,String sessionId,UserPurchase userPurchase)
      {

          Optional<StripeResponse> obj=stripeResponseRepo.findBySessionId(sessionId);
          if(obj.isPresent())
          {
            quizPurchaseService.saveData(userid,userPurchase);
            stripeResponseRepo.deleteBySessionId(sessionId);
            return ResponseEntity.ok("payment Successful");
          }

         return ResponseEntity.status(HttpStatus.CONFLICT).body("Payment failed");
      }
}
