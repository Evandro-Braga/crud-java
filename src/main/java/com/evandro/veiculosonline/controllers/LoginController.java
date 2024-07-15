package com.evandro.veiculosonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository ur;

    @GetMapping("/login")
    public ModelAndView login(){
        User user = new User();
        return new ModelAndView("login", "user", user);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login/getUser")
    public ModelAndView getUser(User user, HttpSession session) {
        User userSession = this.ur.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(userSession == null) {
            return new ModelAndView("login", "ErrorMsg", "Usuario nao encontrado!!");
        }else{
            session.setAttribute("userLogin", userSession);
            return new ModelAndView("index");
        }
    }

    
}
