package com.devinwingo.capstone.services;
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

    //Save User to db
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    //Deletes user by user email
    public void deleteUserByEmail(String email) {
        this.userRepository.deleteById(email);
    }

    //Gets all Users from db
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Gets user by user name
    public Optional<User> getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    //Gets user by email
    public Optional<User> getUserByEmail(String email) {
        Optional<User> optional = userRepository.findById(email);
        return optional;
    }


}
