package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.dao.AuthGroupRepository;
import com.devinwingo.capstone.models.AuthGroup;
import com.devinwingo.capstone.models.Post;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.PostService;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@Slf4j
@SessionAttributes(value = {"currentUser"})
public class HomeController {

    UserService userService;
    PostService postService;
    AuthGroupRepository authGroupRepository;

    @Autowired
    public HomeController(UserService userService, PostService postService, AuthGroupRepository authGroupRepository) {
        this.userService = userService;
        this.postService = postService;
        this.authGroupRepository = authGroupRepository;
    }

    //Mapping for home page
    @GetMapping("/")
    public String homePage(Principal principal, HttpSession session, Model model) {
        try {
            if (principal != null) {
                session.setAttribute("currentUser", userService.getByUserName(principal.getName()));
                log.info("session ID: " + session.getId() + " Value of currentUser: " + session.getAttribute("currentUser").toString());
                log.info(principal.getName());
            }
        } catch (Exception e) {
            log.warn("homePage Exception!!");
            e.printStackTrace();
        }
        //Recent Posts is limited to 20
        model.addAttribute("listPosts", postService.getRecentPosts());
        return "index";
    }

    @GetMapping("/allPosts")
    public String allPostsPage(Model model) {
        //gets all posts from all users to show.
        model.addAttribute("listPosts", postService.getAllPosts());
        return "allPostsView";
    }

    //mapping for login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //Mapping for Registration page
    @GetMapping("/register")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    //Post Mapping for Registration. Save User W/ User Role Automatically. No Mechanism to add Admin privledges
    //>>>>> Need to implement validation
    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        log.warn(user.toString());

        if (userService.getUserByEmail(user.getEmail()).isPresent()){

        }
        if(result.hasErrors()) {
            log.warn("User not validated");
            return "registration";
        }

        userService.saveUser(user);
        authGroupRepository.save(new AuthGroup(user.getEmail(), "ROLE_USER"));
        return "redirect:/login";
    }

}
