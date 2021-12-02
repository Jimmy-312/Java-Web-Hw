package com.project.javaweb.controller;

import java.io.IOException;
import java.util.Date;

import com.project.javaweb.pojo.Files;
import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.util.FileRW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EditorController {
    @Autowired
    private FilesService filesService;
    @Autowired
    private FileSrcService fileSrcService;

    @RequestMapping("/editor")
    public String editor() {
        return "editor";
    }

    @PostMapping("/editor/{fileid}")
    @ResponseBody
    public String editorLoad(@PathVariable("fileid") Integer fileId) throws IOException {
        String content;
        String fileName = fileSrcService.selectByFileId(fileId).getSrc();

        content=FileRW.readFile(fileName);

        return content;
    }

    @GetMapping("/editor/{fileid}")
    public String editorIndex(@PathVariable("fileid") Integer fileId,Model model) throws IOException {
        model.addAttribute("fileid", fileId);
        return "editor";
    }

    @PostMapping("/save/{fileid}")
    @ResponseBody
    public String editorSave(@PathVariable("fileid") Integer fileId,@RequestParam("content") String content) throws IOException {
        Files file = filesService.selectById(fileId);
        String fileName = fileSrcService.selectByFileId(fileId).getSrc();

        FileRW.writeFile(fileName,content);
        file.setUpdatetime(new Date());
        filesService.update(file);

        
        return content;
    }
}
