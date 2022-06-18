package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PostController {

    PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String viewUsersPage(Model model) {
        model.addAttribute("listPosts", postService.getAllPosts());
        return "posts";
    }
}
