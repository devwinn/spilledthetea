package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.dao.CommentRepository;
import com.devinwingo.capstone.dao.PostRepository;
import com.devinwingo.capstone.models.Comment;
import com.devinwingo.capstone.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller @Slf4j
public class CommentController
{

    CommentRepository commentRepository;
    PostRepository postRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/leaveComment/{id}")
    public String createComment(@PathVariable int id, Model model) {
        Comment comment = new Comment();
        Optional<Post> post = this.postRepository.findById(id);
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

}
