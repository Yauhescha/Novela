package com.novelasgame.novelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novelasgame.novelas.entity.DataBase.User;
import com.novelasgame.novelas.service.DataBase.UserServiceImpl;

@Controller
@RequestMapping("/registration")
public class RegisterController {

    @Autowired
    UserServiceImpl userService;

//    @GetMapping
//    public String getRegistration() {
//        return "login";
//    }
    
    @PostMapping
    public String setRegistrationForm(Model model, @ModelAttribute User user,RedirectAttributes redirectAttributes) {
        System.out.println("start registration");
        boolean success = userService.userRegistration(user);
        String response = success ? "Success registration" : "Registration failed";
        System.out.println("registration users: "+response);
        redirectAttributes.addFlashAttribute("success", response);
        return "redirect:/";
    }
}
