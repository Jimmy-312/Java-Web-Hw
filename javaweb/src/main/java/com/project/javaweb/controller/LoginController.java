package com.project.javaweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/login")
    public String displayLogin(HttpSession session) {
        if(session.getAttribute("user")!=null){
            return "redirect:/home/";
        }

        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session,HttpServletRequest request) {
        session.removeAttribute("user");
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String processLogin(HttpSession session,Users userIn,HttpServletRequest request, HttpServletResponse response) {
        Users user = usersService.selectByUserId(userIn.getUserid());

        if(user!=null && userIn.getPasswd().equals(user.getPasswd())){
            session.setAttribute("user", user);
            return "redirect:/home/";
        }

        return "login";
    }
}
