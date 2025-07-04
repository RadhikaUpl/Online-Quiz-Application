package com.radhaa.QUIZAPPLICATIONBackend.Repo;

import com.radhaa.QUIZAPPLICATIONBackend.Entity.ScoreStoringEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreStoreRepo extends JpaRepository<ScoreStoringEntity,Integer>
{

    @Query(value="SELECT u.username ,SUM(best.best_score) as total_score " +
            "from (SELECT user_id , card_id , MAX(score) AS best_score from score_storing_entity GROUP BY  user_id ,card_id ) AS best " +
            "JOIN user_entity u ON u.sign_inid=best.user_id " +
            "GROUP BY best.user_id " +
            "ORDER BY total_score DESC " +
            "LIMIT 5 ",
            nativeQuery = true)
    public List<Object[]> getTopper();

}
