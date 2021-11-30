package com.project.javaweb.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthFile implements Serializable{
    private Integer id;
    private Integer userid;  
    private Integer fileid;
    private String level;
}
