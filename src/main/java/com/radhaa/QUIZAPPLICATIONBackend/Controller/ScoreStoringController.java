package com.radhaa.QUIZAPPLICATIONBackend.Controller;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.ScoreStoringEntity;
import com.radhaa.QUIZAPPLICATIONBackend.Service.scoreStoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/score")
public class ScoreStoringController
{
    @Autowired
    scoreStoringService score;

    @GetMapping("/max")
    public List<Object[]> getTopper()
    {
        return score.getTopper();
    }
}
