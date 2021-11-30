package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.Users;

public interface UsersService {
    void insert(Users user);
    Users selectById(int id);
    List<Users> selectAll();
    void deleteById(int id);
}
