package com.devinwingo.capstone.dao;

import com.devinwingo.capstone.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
