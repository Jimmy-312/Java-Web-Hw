package com.project.javaweb.controller;

import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class UsersController {
    @Autowired
    private UsersService usersService;
    private static int id = 0;
    @RequestMapping("/add")
    public String addUser(){
        Users user = new Users();
        user.setId(id++);
        user.setName("Jimmy");
        usersService.insertUser(user);
        return user.toString();
    }
}
