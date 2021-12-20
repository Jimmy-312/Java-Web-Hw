package com.project.javaweb.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.javaweb.mapper.UsersMapper;
import com.project.javaweb.pojo.Users;
import com.project.javaweb.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper mapper;

    public void insert(Users obj){
        mapper.insert(obj);
    }

    public Users selectById(int id){
        return mapper.selectById(id);
    }

    public Users selectByUserId(String userid){
        QueryWrapper<Users> wrapper =new QueryWrapper<>();
        wrapper.eq("user",userid);
        return mapper.selectOne(wrapper);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<Users> selectAll(){
        return mapper.selectList(null);
    }

    public Users selectByName(String name) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    public void update(Users user){
        QueryWrapper<Users> wrapper =new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        mapper.update(user, wrapper);
    }

}
