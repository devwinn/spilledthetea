package com.devinwingo.capstone.controllers;

import com.devinwingo.capstone.dao.AuthGroupRepository;
import com.devinwingo.capstone.models.AuthGroup;
import com.devinwingo.capstone.models.User;
import com.devinwingo.capstone.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/admin")
public class AdminController {
    UserService userService;
    AuthGroupRepository authGroupRepository;
    @Autowired
    public AdminController(UserService userService, AuthGroupRepository authGroupRepository) {
        this.userService = userService;
        this.authGroupRepository = authGroupRepository;
    }

    //Shows all registered users
    @GetMapping
    public String viewUsersPage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "show_users";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        authGroupRepository.save(new AuthGroup(user.getEmail(), "ROLE_USER"));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //Form to create new user from admin page
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    //Delete User from admin page
    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable (value = "id") String email) {
        userService.deleteUserByEmail(email);
        return "redirect:/admin";
    }
}

//    Not Used. Didn't want to delete. used to update users. "update_user" repurposed to UserController to allow user to edit their own info
//    @GetMapping("/showFormForUpdate/{id}")
//    public String showFormForUpdate(@PathVariable(value = "id") String email, Model model) {
//        if(userService.getUserByEmail(email).isPresent()){
//            User user = userService.getUserByEmail(email).get();
//            model.addAttribute("user", user);
//            return "update_user";
//        } else {
//            log.warn("User Not Found");
//            return "redirect:/admin";
//        }
//    }
