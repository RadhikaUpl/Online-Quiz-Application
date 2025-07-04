package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.CardTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cardRepo extends JpaRepository<CardTable,Integer>
{

    Integer cardid(int cardid);
}
