package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.AuthFile;

public interface AuthFileService {
    void insert(AuthFile obj);
    AuthFile selectById(int id);
    void deleteById(int id);
    List<AuthFile> selectAll();
    AuthFile selectByFileId(int fileid,int userid);
    List<Integer> selectFileIdByUserId(int userid);
    List<AuthFile> selectByFileId(int fileid);
}
 