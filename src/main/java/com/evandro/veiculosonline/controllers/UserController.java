package com.evandro.veiculosonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    
    @GetMapping("/create")
    public String create(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user_register";
    }

    @PostMapping("/create")
    public String save(User user){
        service.create(user);
        return "redirect:/login";
    }
}