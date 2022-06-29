package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Category;
import com.devinwingo.capstone.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "select * from post_categories where post_id = :id", nativeQuery = true)
    List<Category> findAllByPostId(int id);

}
