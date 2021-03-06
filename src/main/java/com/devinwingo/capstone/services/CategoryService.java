package com.devinwingo.capstone.services;

import com.devinwingo.capstone.dao.CategoryRepository;
import com.devinwingo.capstone.models.Category;
import com.devinwingo.capstone.models.Post;
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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Gets all Categories to list in allCategoriesView.html
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    //Gets category by category name
    public Optional<Category> getCategoryByName(String catName) {
        return this.categoryRepository.findById(catName);
    }

}
