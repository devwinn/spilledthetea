package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
