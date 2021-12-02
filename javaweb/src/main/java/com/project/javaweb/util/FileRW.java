package com.project.javaweb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileRW {
    private static String filepath = "D://";

    public static void writeFile(String path, String content) throws UnsupportedEncodingException {
        File file = new File(filepath + path);
        byte[] bData = content.getBytes("UTF-8");
        FileOutputStream fis;

        try {
            fis = new FileOutputStream(file);
            fis.write(bData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path) throws UnsupportedEncodingException {
        File file = new File(filepath + path);
        Long fileLength = file.length();
        byte[] bData = new byte[fileLength.intValue()];
        FileInputStream fis;

        try {
            fis = new FileInputStream(file);
            fis.read(bData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(bData, "UTF-8");
    }
}
