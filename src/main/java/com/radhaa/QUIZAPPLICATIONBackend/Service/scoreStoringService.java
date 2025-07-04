package com.radhaa.QUIZAPPLICATIONBackend.Service;

import com.radhaa.QUIZAPPLICATIONBackend.Repo.ScoreStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class scoreStoringService
{
    @Autowired
    ScoreStoreRepo scoreStoreRepo;
    public List<Object[]> getTopper()
    {
        return scoreStoreRepo.getTopper();
    }


}
