package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Category;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.CategoryService;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/posts")
public class PostController {

    PostService postService;
    UserService userService;
    CategoryService categoryService;
    @Autowired
    public PostController(PostService postService, UserService userService, CategoryService categoryService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    //mapping for post creation
    //**Add user authorization
    @GetMapping("/createpost")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute(("allCategories"), categoryService.getAllCategories());
        log.warn(post.toString());
        return "create_post";
    }

    //add/implement current user
    //postmapping for post creation
    @PostMapping("/savepost")
    public String createPost(@ModelAttribute("post") Post post, Principal principal) {
       //for testing user is hardcoded
        log.info(post.toString());

        User user = this.userService.getUserByEmail("dev@gmail.com");
        log.warn(user.toString());

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
            model.addAttribute("postId",post1.getId());
            model.addAttribute("post", post1);
            model.addAttribute("listComments", post1.getComments());

        } else {
            return "error";
        }

        return "post";
    }
}
