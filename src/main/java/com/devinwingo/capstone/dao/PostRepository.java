package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
   //Queries DB for all posts by user. user is identified by email
   @Query(value = "select * from post where user_email = :email", nativeQuery = true)
   List<Post> findAllByUser(String email);

   @Query(value = "select * from post order by date desc limit 20", nativeQuery = true)
   List<Post> findRecentPosts();
}
