package com.project.javaweb.controller;

import java.util.List;

import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    private static int id = 0;

    @GetMapping("/adduser")
    @ResponseBody
    public String addUser(){
        Users user = new Users();
        user.setId(id++);
        user.setName("Jimmy");
        usersService.insertUser(user);
        return user.toString();
    }

    @RequestMapping("/getuser/{id}")
    @ResponseBody
    public String getUser(@PathVariable("id") int id){
        Users user = new Users();
        user = usersService.selectUserById(id);
        return user.toString();
    }

    @RequestMapping("/getallusers")
    public String getAllUser(Model model){
        List<Users> usersList = usersService.selectAllUsers();
        for(Users i : usersList){
            model.addAttribute("user"+i.getId().toString(), i);
        }
        return "users";
    }
}
