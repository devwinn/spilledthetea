package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@Slf4j
public class HomeController {

    UserService userService;
    PostService postService;
    public HomeController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    //Mapping for home page
    @GetMapping("/")
    public String homePage(Model model) {
        //gets all posts from all users to show on homepage
        //**need to add query to limit posts to 10-20
        model.addAttribute("listPosts", postService.getAllPosts());
        return "home";
    }

    //mapping for login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
