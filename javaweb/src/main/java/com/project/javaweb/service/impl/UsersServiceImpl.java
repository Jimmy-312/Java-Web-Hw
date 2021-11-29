package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.UsersMapper;
import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    public void insertUser(Users user){
        usersMapper.insert(user);
    }

    public Users selectUserById(int id){
        return usersMapper.selectById(id);
    }

    public List<Users> selectAllUsers(){
        return usersMapper.selectList(null);
    }

}
