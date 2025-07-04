package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.UserQuizPurchase;
import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizPurchaseRepo extends JpaRepository<UserQuizPurchase,Integer>
{
    public List<UserQuizPurchase> findByUser(userTable obj);
}
