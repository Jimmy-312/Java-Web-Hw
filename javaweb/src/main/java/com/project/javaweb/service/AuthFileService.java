package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.AuthFile;

public interface AuthFileService {
    void insert(AuthFile file);
    AuthFile selectFileById(int id);
    void deleteById(int id);
    List<AuthFile> selectAllFiles();
}
