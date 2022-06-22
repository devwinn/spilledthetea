package com.devinwingo.capstone.services;

import com.devinwingo.capstone.dao.PostRepository;
import com.devinwingo.capstone.dao.UserRepository;
import com.devinwingo.capstone.models.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public void deleteUserByEmail(String email) {
        this.userRepository.deleteById(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User getUserByEmail(String email) {
        Optional<User> optional = userRepository.findById(email);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException("User not found");
        }
        return user;
    }


}
