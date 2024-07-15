package com.evandro.veiculosonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository ur;
    
    @GetMapping("/user/register")
    public ModelAndView saveUser(){
        User user = new User();
        return new ModelAndView("userRegister", "user", user);
    }

    @PostMapping("/user/save")
    public String SalvarUsuario(User user){
        this.ur.save(user);
        return "redirect:/login";
    }
}