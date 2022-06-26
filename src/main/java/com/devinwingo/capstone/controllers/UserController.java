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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller @Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    PostService postService;
    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    //mapping for user profile
    //**need to add current user username to mapping
    @GetMapping("profile")
    public String goToProfile(Model model) {
        //**for testing purposes. need to use current session user when security is implemented
        Optional <User> user = userService.getByUserName("devwin");

        if (user.isPresent()){
            User current = user.get();
            model.addAttribute("listPosts", postService.getUserPosts(current.getEmail()));
            log.info("successfully redirected to user profile");
            return "userPosts";
        } else {
            log.warn("something went wrong");
            return "redirect:/";
        }


    }

}
