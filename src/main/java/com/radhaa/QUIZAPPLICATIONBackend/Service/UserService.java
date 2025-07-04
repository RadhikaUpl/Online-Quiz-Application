package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.otpVerification;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.otpRepo;
import com.radhaa.QUIZAPPLICATIONBackend.Repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    userRepo userRepo;
    @Autowired
    otpRepo otprepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       userTable users= userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"+username));
        return User.withUsername(users.getUsername())
                .password(users.getPassword())
                .build();
    }

    public ResponseEntity<String> addNewUser(userTable user,int inputOTP)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<otpVerification> optionalOtp=otprepo.findByMail(user.getEmail());
        if(optionalOtp.isPresent())
        {
            otpVerification otp= optionalOtp.get();
            if( otp.getMail().equals(user.getEmail()) && otp.getOtp()==inputOTP && LocalDateTime.now().isBefore(otp.getExpiryTime()) )
            {
                userRepo.save(user);
                otprepo.delete(optionalOtp.get());
                return ResponseEntity.ok("otp is valid");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("otp is invalid");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not presemt");
    }

    public ResponseEntity<String> userValidation(userTable user) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        if (!(user.getEmail().matches(regex))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid email format");
        }
        Optional<userTable> email = userRepo.findByEmail(user.getEmail());
        if (email.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email already exists");
        }
        Optional<userTable> name = userRepo.findByUsername(user.getUsername());
        if (name.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
        }
        String password = user.getPassword();
       if(password.length()<8)
       {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password must be of minimum 8 length");
       }
        return ResponseEntity.ok("All details are valid Otp send");

    }

}
