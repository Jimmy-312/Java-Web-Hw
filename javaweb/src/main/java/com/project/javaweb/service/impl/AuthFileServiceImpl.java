package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.AuthFileMapper;
import com.project.javaweb.pojo.AuthFile;
import com.project.javaweb.service.AuthFileService;

import org.springframework.beans.factory.annotation.Autowired;

public class AuthFileServiceImpl implements AuthFileService{
    @Autowired
    private AuthFileMapper mapper;

    public void insert(AuthFile obj){
        mapper.insert(obj);
    }

    public AuthFile selectById(int id){
        return mapper.selectById(id);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<AuthFile> selectAll(){
        return mapper.selectList(null);
    }
}
