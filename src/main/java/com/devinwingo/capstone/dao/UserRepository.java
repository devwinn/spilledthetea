package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);

}
