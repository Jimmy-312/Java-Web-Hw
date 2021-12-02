package com.project.javaweb.service;

import java.util.List;

import com.project.javaweb.pojo.FileSrc;

public interface FileSrcService {
    void insert(FileSrc obj);
    FileSrc selectById(int id);
    void deleteById(int id);
    List<FileSrc> selectAll();
    void update(FileSrc obj);
    FileSrc selectByFileId(int fileid);
}
