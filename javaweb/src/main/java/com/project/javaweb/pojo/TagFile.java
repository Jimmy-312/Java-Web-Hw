package com.project.javaweb.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TagFile implements Serializable{
    private Integer id;
    private String name;
    private Integer fileid;
}
