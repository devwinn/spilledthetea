package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    public void findByUserName() {
        User user = User.builder()
                .firstName("carl")
                .lastName("weathers")
                .userName("cweathers")
                .password("password")
                .email("cweathers@gmail.com")
                .build();
        userRepository.save(user);

        assertEquals(userRepository.findByUserName("cweathers"), userRepository.findByUserName(user.getUserName()));

    }
}