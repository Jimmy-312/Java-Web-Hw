package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.AuthFileMapper;
import com.project.javaweb.pojo.AuthFile;
import com.project.javaweb.service.AuthFileService;

import org.springframework.beans.factory.annotation.Autowired;

public class AuthFileServiceImpl implements AuthFileService{
    @Autowired
    private AuthFileMapper authFileMapper;

    public void insert(AuthFile authFile){
        authFileMapper.insert(authFile);
    }

    public AuthFile selectFileById(int id){
        return authFileMapper.selectById(id);
    }

    public void deleteById(int id){
        authFileMapper.deleteById(id);
    }

    public List<AuthFile> selectAllFiles(){
        return authFileMapper.selectList(null);
    }
}
