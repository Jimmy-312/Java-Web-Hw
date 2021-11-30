package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.FileSrc;

public interface FileSrcService {
    void insert(FileSrc file);
    FileSrc selectFileSrcById(int id);
    void deleteById(int id);
    List<FileSrc> selectAllFileSrc();
}
