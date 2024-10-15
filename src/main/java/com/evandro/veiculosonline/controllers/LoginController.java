package com.evandro.veiculosonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.evandro.veiculosonline.models.User;
import com.evandro.veiculosonline.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository ur;

    @GetMapping
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping
    public String getUser(User user, HttpSession session, Model model) {
        User userSession = this.ur.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userSession == null) {
            model.addAttribute("ErrorMsg", "Usuario nao encontrado!!");
            return "login";
        }
        session.setAttribute("userLogin", userSession);
        return "index";
    }

    @GetMapping("/out")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
