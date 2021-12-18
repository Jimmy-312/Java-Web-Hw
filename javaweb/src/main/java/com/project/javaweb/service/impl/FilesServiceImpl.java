package com.project.javaweb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.javaweb.mapper.FilesMapper;
import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesServiceImpl implements FilesService {
    @Autowired
    private FilesMapper mapper;

    public void insert(Files obj) {
        mapper.insert(obj);
    }

    public Files selectById(int id) {
        return mapper.selectById(id);
    }

    public Files selectByName(String name) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return mapper.selectOne(wrapper);
    }

    public void update(Files files){
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("id", files.getId());
        mapper.update(files, wrapper);
    }

    public boolean uploadFile(String fileName, MultipartFile fileContent) {
        String filePath = "/cloudfiles/";
        File dest = new File(filePath + fileName);
        try {
            fileContent.transferTo(dest);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Files> selectByOwner(int ownerid) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("ownerid", ownerid);
        return mapper.selectList(wrapper);
    }

    public Page<Files> selectByPublic(String ispublic,Integer pageNum) {
        Page<Files> page = new Page<>(pageNum,8);
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("ispublic", ispublic);
        return mapper.selectPage(page, wrapper);
    }

    public void deleteByIds(List<Integer> ids) {

        mapper.deleteBatchIds(ids);
    }

    public List<Files> selectAll() {
        return mapper.selectList(null);
    }

    public Page<Files> selectByIds(List<Integer> idList,Integer pageNum){
        Page<Files> list = new Page<>();
        //Integer sum;
        if(idList.isEmpty()){
            return list;
        }
        List<Files> fileList = mapper.selectBatchIds(idList);
        //sum = idList.size()/8+((idList.size()%8!=0)?1:0);
        list.setRecords(fileList.subList(8*(pageNum-1), ((pageNum)*8<=idList.size())?pageNum*8:idList.size()));
        list.setCurrent(pageNum);
        list.setTotal(fileList.size());
        list.setSize(8);
        return list;
    }

	@Override
	public List<Files> selectByIds(List<Integer> idList) {
        List<Files> list = new ArrayList<>();
        if(idList.isEmpty()){
            return list;
        }
        return mapper.selectBatchIds(idList);
	}

	@Override
	public List<Files> selectByPublic(String string) {
		QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("ispublic", string);
        return mapper.selectList(wrapper);
	}
}
