package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.otpVerification;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.otpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService
{

        @Autowired
        otpRepo otprepo;

        @Autowired
        private JavaMailSender mailSender;

        public void sendOtpEmail(String toEmail, int otp)
        {
                otpVerification otpuser=new otpVerification();
                otpuser.setOtp(otp);
                otpuser.setMail(toEmail);
                otpuser.setExpiryTime(LocalDateTime.now().plusMinutes(5));

                otprepo.save(otpuser);

                SimpleMailMessage message = new SimpleMailMessage();//simpleMailMessage is a class
                message.setTo(toEmail);
                message.setSubject("Your OTP Code");
                message.setText("Your OTP is: " + otp);

                mailSender.send(message);
    }

//        public ResponseEntity<String> otpVerify(String email , int inputOTP)
//        {
//                Optional<otpVerification> optionalOtp=otprepo.findByMail(email);
//                if(optionalOtp.isPresent())
//                {
//                        otpVerification otp= optionalOtp.get();
//                        if( otp.getMail().equals(email) && otp.getOtp()==inputOTP && LocalDateTime.now().isBefore(otp.getExpiryTime()) )
//                        {
//                               return ResponseEntity.ok("otp is valid");
//                        }
//                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("otp is invalid");
//                }
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not presemt");
//        }

}
