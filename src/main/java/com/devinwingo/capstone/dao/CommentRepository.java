package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
