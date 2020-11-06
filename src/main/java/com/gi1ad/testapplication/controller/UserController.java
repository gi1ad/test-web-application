package com.gi1ad.testapplication.controller;

import com.gi1ad.testapplication.domain.User;
import com.gi1ad.testapplication.repository.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String user(Model model) {
        model.addAttribute("users", repository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "userEdit";
    }


    @PostMapping
    public String saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String email,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("userId") User user) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDate(date);
        repository.save(user);
        return "redirect:/user";
    }


    @GetMapping("profile")
    public String getUser(Authentication authentication, Model model) {
        String name = authentication.getName();
        User user = repository.findByUsername(name);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("date", user.getDate());
        model.addAttribute("id", user.getId());
        return "profile";
    }

    @PostMapping("profile")
    public String updateUserProfile(@RequestParam String username, @RequestParam String password, @RequestParam String email,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, @RequestParam("userId") User user) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDate(date);
        repository.save(user);
        return "redirect:/user/profile";
    }

    @GetMapping("new")
    public String newUser() {
        return "newUser";
    }

    @PostMapping("new")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, User user) {
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDate(date);
        repository.save(user);
        return "redirect:/user";
    }

    @PostMapping("delete")
    public String deleteUser(@RequestParam Long id){
        repository.deleteById(id);
        return "redirect:/user";


    }


}
