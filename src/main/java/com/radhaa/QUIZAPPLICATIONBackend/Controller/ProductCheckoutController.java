package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.DTO.ProductRequest;
import com.radhaa.QUIZAPPLICATIONBackend.DTO.UserPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.StripeResponse;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.UserQuizPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckoutController
{
    @Autowired
    private StripeService stripeService;
    @Autowired
    userRepo userrepo;
    public ProductCheckoutController(StripeService stripeService)
    {
        this.stripeService=stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest)
    {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stripeResponse);
    }
    @PostMapping("/sessionId")
    public ResponseEntity<String> checkSessionId(@RequestBody UserPurchase userPurchase, @RequestParam String sessionId)
    {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        userTable user = userrepo.findByUsername(name).orElseThrow();

         int userId=user.getSignInid();
            return stripeService.checkSessionId(userId,sessionId,userPurchase);
    }




}
