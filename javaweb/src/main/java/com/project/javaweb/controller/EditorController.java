package com.project.javaweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.project.javaweb.service.FileSrcService;
import com.project.javaweb.service.FilesService;
import com.project.javaweb.util.Word2Html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private String path = "D://";

    @RequestMapping("/editor")
    public String editor() {
        return "editor";
    }

    @PostMapping("/editor/{filename}")
    @ResponseBody
    public String editorLoad(@PathVariable("filename") String fileName) throws IOException {
        String content;
        File file = new File(path+fileName);
        Long fileLength = file.length();
        byte[] bData=new byte[fileLength.intValue()];
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
            fis.read(bData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        content = new String(bData,"UTF-8");
        System.out.println(content);
        // String htmlText = Word2Html.Doc2Html("D://", fileName);

        // Word2Html.html2Word(htmlText, "321", "D://");
        return content;
    }

    @PostMapping("/save/{filename}")
    @ResponseBody
    public String editorSave(@PathVariable("filename") String fileName,@RequestParam("content") String content) throws IOException {
        System.out.println(content);
        File file = new File(path+fileName);
        byte[] bData=content.getBytes("UTF-8");
        FileOutputStream fis;

        try {
            fis = new FileOutputStream(file);
            fis.write(bData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        // String htmlText = Word2Html.Doc2Html("D://", fileName);

        // Word2Html.html2Word(htmlText, "321", "D://");
        return content;
    }
}
