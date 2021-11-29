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
    private FilesMapper filesMapper;

    public void insert(Files file){
        filesMapper.insert(file);
    }

    public Files selectFileById(int id){
        return filesMapper.selectById(id);
    }

    public void deleteById(int id){
        filesMapper.deleteById(id);
    }

    public List<Files> selectAllFiles(){
        return filesMapper.selectList(null);
    }
}
