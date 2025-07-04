package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.userTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface userRepo extends JpaRepository<userTable, Integer>
{
    Optional<userTable> findByUsername(String username);

    Optional<userTable> findByEmail(String email);
}
