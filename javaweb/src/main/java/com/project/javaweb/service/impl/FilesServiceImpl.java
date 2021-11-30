package com.project.javaweb.service.impl;

import java.util.List;

import com.project.javaweb.mapper.FilesMapper;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilesServiceImpl implements FilesService{
    @Autowired
    private FilesMapper mapper;

    public void insert(Files obj){
        mapper.insert(obj);
    }

    public Files selectById(int id){
        return mapper.selectById(id);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public List<Files> selectAll(){
        return mapper.selectList(null);
    }
}
