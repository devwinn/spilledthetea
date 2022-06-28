package com.devinwingo.capstone.security;


//Step 3.2

import com.devinwingo.capstone.dao.AuthGroupRepository;
import com.devinwingo.capstone.models.AuthGroup;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class AppUserDetailsService implements UserDetailsService {

    AuthGroupRepository authGroupRepository;
    UserService userService;

    @Autowired
    public AppUserDetailsService(AuthGroupRepository authGroupRepository, UserService userService) {
        this.authGroupRepository = authGroupRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<AuthGroup> authGroupList = authGroupRepository.findByaEmail(username);
        Optional<User> optional = userService.getUserByEmail(username);

        if (optional.isPresent()) {
            User user = optional.get();
            return new AppUserPrincipal(user, authGroupList);
        } else {
            throw new UsernameNotFoundException("No User Found");
        }
    }
}
