package com.radhaa.QUIZAPPLICATIONBackend.Repo;


import com.radhaa.QUIZAPPLICATIONBackend.Entity.StripeResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StripeResponseRepo extends JpaRepository<StripeResponse, Integer>
{
    public Optional<StripeResponse> findBySessionId(String sessionId);
    @Transactional
    public void deleteBySessionId(String sessionId);
}
