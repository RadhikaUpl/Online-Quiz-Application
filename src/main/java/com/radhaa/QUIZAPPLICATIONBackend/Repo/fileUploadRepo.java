package com.radhaa.QUIZAPPLICATIONBackend.Repo;


import com.radhaa.QUIZAPPLICATIONBackend.Entity.fileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface fileUploadRepo extends JpaRepository<fileUploadEntity,Integer>
{
    public Optional<fileUploadEntity> findByName(String name);
}
