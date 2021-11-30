package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.Files;

public interface FilesService {
    int insert(Files file);
    Files selectById(int id);
    void deleteById(int id);
    List<Files> selectAll();
    Files selectByName(String name);
}
