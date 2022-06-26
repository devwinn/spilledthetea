package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Comment;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller @Slf4j
public class CommentController
{
    PostService postService;
    UserService userService;

    @Autowired
    public CommentController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping("/leaveComment/{id}")
    public String createComment(@PathVariable int id, Model model) {
        Comment comment = new Comment();
        Optional<Post> post = this.postService.getById(id);
        if (post.isPresent()) {
            Post post1 = post.get();
            model.addAttribute("comment", comment);
            model.addAttribute("post", post1);
            log.warn(post1.toString());

            return "create_comment";
        } else {
            return "/post/" + id;
        }
    }

    @PostMapping("/saveComment/{id}")
    public String saveComment(@PathVariable int id, @ModelAttribute("comment") Comment comment) {
        Optional<Post> optionalPost = this.postService.getById(id);
        log.info(comment.toString());
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.addComment(comment);
            comment.setUserName(post.getUser().getUserName());
            postService.savePost(post);
            log.info(post.toString());
            log.info(comment.toString());
            return "redirect:/posts/post/" + post.getId();
        } else {
            return "create_comment";
        }
    }

}
