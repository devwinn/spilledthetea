package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Category;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.CategoryService;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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
        log.info(post.toString());
        if(principal != null) {
            Optional<User> optional = userService.getByUserName(principal.getName());
            if (optional.isPresent()) {
                User user = optional.get();
                user.addPost(post);
                userService.saveUser(user);

                log.info(user.toString());
                log.info(post.toString());
            }
        } else {
            log.warn("no principal found");
            return "error";
        }
        return "redirect:/profile";
    }

    //Need to add user authorization
    @GetMapping("/post/{id}")
    public String goToPost(@PathVariable int id, Model model) {

        Optional<Post> post = this.postService.getById(id);

        if (post.isPresent()) {
            Post post1 = post.get();
            model.addAttribute("categories",post1.getCategories());
            model.addAttribute("post", post1);
            model.addAttribute("listComments", post1.getComments());
        } else {
            return "error";
        }
        return "post";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable int id, Principal principal, Model model) {
        User currentUser = postService.getById(id).get().getUser();
        Post post = postService.getById(id).get();
        if(principal != null){
            if(currentUser.equals(userService.getByUserName(principal.getName()).get())) {
                postService.deleteUserPost(post);
                log.info("Post deleted");
            } else {
                log.warn("Current User is not post author");
            }
            return "redirect:/profile";
        } else {
            log.warn("Principal is null");
            return "redirect:/";
        }
    }
}
