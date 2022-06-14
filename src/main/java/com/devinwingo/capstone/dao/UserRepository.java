package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
