package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/posts")
public class PostController {

    PostService postService;
    UserService userService;
    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    //mapping for post creation
    //**Add user authorization
    @GetMapping("/createpost")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        log.warn(post.toString());
        return "create_post";
    }

    //add/implement current user
    //postmapping for post creation
    @PostMapping("/savepost")
    public String createPost(@ModelAttribute("post") Post post) {
       //for testing user is hardcoded
        User user = this.userService.getUserByEmail("dev@gmail.com");
        user.addPost(post);
        userService.saveUser(user);

        log.info(user.toString());
        log.info(post.toString());
        return "redirect:/";
    }

    //Need to add user authorization
    @GetMapping("/post/{id}")
    public String goToPost(@PathVariable int id, Model model) {

        Optional<Post> post = this.postService.getById(id);

        if (post.isPresent()) {
            Post post1 = post.get();
            model.addAttribute("post", post1);
        } else {
            return "error";
        }

        return "post";
    }
}
