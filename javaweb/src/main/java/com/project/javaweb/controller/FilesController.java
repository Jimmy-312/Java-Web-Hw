package com.project.javaweb.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;

import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FilesController {
    @Autowired
    private FilesService filesService;
    // @Autowired
    // private FileSrcService fileSrcService;

    @RequestMapping("/home")
    public String getAllFiles(Model model){
        List<Files> filesList = filesService.selectAllFiles();
        model.addAttribute("filelist",filesList);

        return "files";
    }

    @PostMapping("/addfile")
    public String addNewFile(@Validated Files file){
        //Files file = new Files();
         

        return "OK";
    }

}
