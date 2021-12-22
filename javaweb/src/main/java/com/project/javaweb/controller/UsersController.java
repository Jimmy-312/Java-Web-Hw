package com.project.javaweb.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/changepasswd")
    @ResponseBody
    public String changePassswd(@RequestParam("old") String old,@RequestParam("new") String newpass,HttpSession session){
        Users user = (Users) session.getAttribute("user");
        if (old.equals(user.getPasswd())){
            user.setPasswd(newpass);
            usersService.update(user);
        }else{
            return "err";
        }

        return "okay";
    }

    @GetMapping("/signup")
    public String register(){
        return "register";
    }

    @PostMapping("/signup")
    public String registerPost(@RequestParam Map<String,String> info){
        Users user = new Users();

        if(info.get("code").equals("bjt")){
            return "secret";
        }

        user.setLevel("0");
        user.setName(info.get("name"));
        user.setPasswd(info.get("passwd"));
        user.setUserid(info.get("userid"));
        user.setGender(info.get("gender"));

        usersService.insert(user);
        return "redirect:/home";
    }
}
