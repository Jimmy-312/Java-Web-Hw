package com.project.javaweb.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Users implements Serializable{
    private Integer id;
    private String name;
    private String gender;
    private String passwd;
    private String level;

    @Override
    public String toString(){
        return "{id:" + id + ",name:" + name + ",gender:"+ gender + ",level:" + level +"}";
    }
}
