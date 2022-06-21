package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
public class PostController {

    PostService postService;
    UserService userService;
    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/createpost")
    public String createPost(Model model) {
        Post newPost = new Post();
        User user = new User();
        model.addAttribute("post", newPost);
        model.addAttribute("user", user);
        return "create_post";
    }

    @PostMapping("/createpost")
    public String createPost(@ModelAttribute User user, @ModelAttribute Post post) {
        Optional<User> optionalUser = this.userService.getByUserName(user.getUserName());
        if (optionalUser.isPresent()) {
            System.out.println("not empty");
            User user1 = optionalUser.get();
            post.setUser(user1);
            this.postService.savePost(post);
            return "redirect:/post/" + post.getId();

        } else {
            if (optionalUser.isEmpty()) {
                System.out.println("empty user");
            }
            return "error";
        }
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
