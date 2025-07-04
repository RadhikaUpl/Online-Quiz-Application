package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.QuestionTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface questionRepo extends JpaRepository<QuestionTable ,Integer>
{
    public List<QuestionTable> findByQuestionType(String type);
}
