package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query(value = "SELECT DISTINCT r FROM Rating r WHERE r.id=:id")
    Rating findByRatingId(Integer id);
}
