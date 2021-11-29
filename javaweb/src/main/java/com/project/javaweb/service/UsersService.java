package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.Users;

public interface UsersService {
    void insertUser(Users user);
    Users selectUserById(int id);
    List<Users> selectAllUsers();
}
