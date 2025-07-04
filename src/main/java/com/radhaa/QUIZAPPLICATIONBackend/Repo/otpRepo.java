package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.otpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface otpRepo extends JpaRepository<otpVerification, Integer>
{
    Optional<otpVerification> findByMail(String mail);
}
