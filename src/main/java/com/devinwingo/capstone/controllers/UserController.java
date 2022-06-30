package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/profile")
public class UserController {

    UserService userService;
    PostService postService;
    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    //mapping for user profile
    @GetMapping
    public String goToProfile(Model model, Principal principal) {
        log.warn(principal.getName());
        if(principal != null) {
            //Retrieves user by taking the current session principal and searching for user by username
            Optional<User> optional = userService.getByUserName(principal.getName());
            if (optional.isPresent()){
                User user = optional.get();
                //Add User posts to model
                model.addAttribute("listPosts", postService.getUserPosts(user.getEmail()));
                //Add User to model
                model.addAttribute("user", user);
                log.info("successfully redirected to user profile");
                log.info(user.toString());
                return "userPosts";
            } else {
                //Return to login if principal is not found
                return "redirect:/login";
            }
        } else {
            log.warn("Principal is null");
            return "redirect:/login";
        }
    }

    @GetMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String email, Model model) {
        if(userService.getUserByEmail(email).isPresent()){
            User user = userService.getUserByEmail(email).get();
           //add user info to the model to populate current info. when form is submitted it will submit with populated and changed info
            model.addAttribute("user", user);
            log.info("User info before update: " + user.toString());
            return "update_user";
        } else {
            log.warn("User Not Found");
            return "redirect:/profile";
        }
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        //Save user to db
        userService.saveUser(user);
        log.info("User info after update: " + user.toString());
        return "redirect:/profile";
    }

}
