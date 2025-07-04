package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/Check")
public class UserLoginController
{
    @GetMapping("/islogin")
    public ResponseEntity<?> isLogin()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser")))
        {
            return ResponseEntity.ok(Map.of("islogin",true,"username",authentication.getName()));
        }
        return ResponseEntity.ok(Map.of("islogin",false));
    }

}
