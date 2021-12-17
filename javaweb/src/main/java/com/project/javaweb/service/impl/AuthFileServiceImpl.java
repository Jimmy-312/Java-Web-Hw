package com.project.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.javaweb.mapper.AuthFileMapper;
import com.project.javaweb.pojo.AuthFile;
import com.project.javaweb.service.AuthFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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

    public AuthFile selectByFileId(int fileid,int userid){
        QueryWrapper<AuthFile> wrapper = new QueryWrapper<>();
        wrapper.eq("fileid", fileid);
        wrapper.eq("userid", userid);
        return mapper.selectOne(wrapper);
    }

    public List<Integer> selectFileIdByUserId(int userid) {
        QueryWrapper<AuthFile> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid);
        List<AuthFile> authList = mapper.selectList(wrapper);
        List<Integer> fileIdList = new ArrayList<Integer>();
        for (AuthFile auth : authList){
            fileIdList.add(auth.getFileid());
        }
        return fileIdList;
    }

    public List<AuthFile> selectByFileId(int fileid) {
        QueryWrapper<AuthFile> wrapper = new QueryWrapper<>();
        wrapper.eq("fileid", fileid);
        List<AuthFile> authList = mapper.selectList(wrapper);

        return authList;
    }

    public void update(AuthFile authFile){
        QueryWrapper<AuthFile> wrapper = new QueryWrapper<>();
        wrapper.eq("id", authFile.getId());
        mapper.update(authFile, wrapper);
    }

    public void deleteByRelate(Integer userid, Integer fileid){
        QueryWrapper<AuthFile> wrapper = new QueryWrapper<>();
        wrapper.eq("fileid", fileid);
        wrapper.eq("userid", userid);
        mapper.delete(wrapper);
    }
}
