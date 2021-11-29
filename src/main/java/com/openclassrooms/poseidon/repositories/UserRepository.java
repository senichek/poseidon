package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT DISTINCT u FROM User u WHERE u.id=:id")
    User findByUsereId(Integer id);

    User findByUsername(String username);
}
