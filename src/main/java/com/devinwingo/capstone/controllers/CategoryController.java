package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.Category;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.services.CategoryService;
import com.devinwingo.capstone.services.PostService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/categories")
public class CategoryController {

    CategoryService categoryService;
    PostService postService;
    @Autowired
    public CategoryController(CategoryService categoryService, PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }

    @GetMapping
    public String showAllCategories(Model model) {
        //Create List of All Categories
        List<Category> categories = categoryService.getAllCategories();
        //Add Categories to Model
        model.addAttribute("categories", categories);
        return "allCategoriesView";
    }

    @GetMapping("/{name}")
    public String showCategoryByName(@PathVariable String name, Model model) {
        //Get category by using path variable to search for category by name
        Category category = categoryService.getCategoryByName(name).get();
        //Get Posts from category
        List<Post> posts = category.getPosts();
        //Add variable to model
        model.addAttribute("category", category);
        model.addAttribute("posts", posts);
        return "postsByCategory";
    }

}
