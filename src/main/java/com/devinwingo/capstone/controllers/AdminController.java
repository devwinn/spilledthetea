package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(value = "/admin")
public class AdminController {
    UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String viewUsersPage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "show_users";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") String email, Model model) {
        if(userService.getUserByEmail(email).isPresent()){
            User user = userService.getUserByEmail(email).get();
            model.addAttribute("user", user);
            return "update_user";
        } else {
            log.warn("User Not Found");
            return "redirect:/admin";
        }

    }
    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable (value = "id") String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/admin";
    }
}
