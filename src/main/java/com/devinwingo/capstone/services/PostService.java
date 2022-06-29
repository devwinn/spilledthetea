package com.devinwingo.capstone.services;

import com.devinwingo.capstone.dao.PostRepository;
import com.devinwingo.capstone.dao.UserRepository;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
@Slf4j
public class PostService {

    PostRepository postRepository;

    UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getRecentPosts() {
        return postRepository.findRecentPosts();
    }

    public List<Post> getUserPosts(String email) {
        return postRepository.findAllByUser(email);
    }

    public Optional<Post> getById(int id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post) {
        return this.postRepository.saveAndFlush(post);
    }

    public void deleteUserPost(Post post) {
        User user = post.getUser();
        log.info("current user: " + user.toString());
        log.info("post to delete" + post.toString());
        post.deleteCategories();
        this.postRepository.saveAndFlush(post);
        user.deletePost(post);
        this.postRepository.delete(post);
    }
}
