package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Service.EmailService;
import com.radhaa.QUIZAPPLICATIONBackend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class userController
{
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestBody userTable user)
    {
        ResponseEntity<String> result=userService.userValidation(user);
        if(result.getStatusCode()== HttpStatusCode.valueOf(200))
        {
            sendOtp(user.getEmail());
        }
        return result;
    }

    public void sendOtp(String mail)
    {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        emailService.sendOtpEmail( mail , otp);
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> AddNewUser(@RequestBody userTable user ,@RequestParam int otp)
    {
        return userService.addNewUser(user,otp);

    }


}
