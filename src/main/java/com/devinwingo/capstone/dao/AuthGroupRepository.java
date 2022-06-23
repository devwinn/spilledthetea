package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {

    //select * from auth_group where aemail = :email
    List<AuthGroup> findByaEmail(String email);
}
