package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Comment;
import com.devinwingo.capstone.models.Post;
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
public class CommentController
{
    PostService postService;
    UserService userService;

    @Autowired
    public CommentController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    //Returns form to create Comment on Post. Post is retrieved via path variable id
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

    //Current logged in User is set to Comment. Comment is Saved to Post then Current User is redirected back to post
    @PostMapping("/saveComment/{id}")
    public String saveComment(@PathVariable int id, @ModelAttribute("comment") Comment comment, Principal principal) {
        Optional<Post> optionalPost = this.postService.getById(id);
        //Checks for Principal in order to set userName for Comment
        if (principal != null) {
            Optional<User> optional = userService.getByUserName(principal.getName());
            if (optional.isPresent()) {
                User user = optional.get();
                comment.setUserName(user.getUserName());
                log.info(user.toString());
                log.info("Comment User Name: " + comment.getUserName());
            } else {
                log.warn("User not found");
            }
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                post.addComment(comment);
                postService.savePost(post);
                log.info(post.toString());
                log.info(comment.toString());
                return "redirect:/posts/post/" + post.getId();

            } else {
                log.warn("Post not found ");
            }
        }
        return "create_comment";
    }

}
