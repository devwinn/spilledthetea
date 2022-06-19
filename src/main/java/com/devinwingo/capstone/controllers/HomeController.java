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

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("listPosts", postService.getAllPosts());
        return "home";
    }

}
