package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//step 2
@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {

    //select * from auth_group where userName = :username
    List<AuthGroup> findByaEmail(String username);
}
