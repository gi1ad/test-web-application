package com.gi1ad.testapplication.controller;


import com.gi1ad.testapplication.domain.Role;
import com.gi1ad.testapplication.domain.User;
import com.gi1ad.testapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserRepository repository;

    public RegistrationController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,Map<String, Object> model) {
        User userFromDb = repository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User Already Exists");
            return "registration";
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            repository.save(user);
            return "redirect:/login";
        }
    }


}
