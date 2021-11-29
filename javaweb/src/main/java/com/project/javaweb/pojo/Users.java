package com.project.javaweb.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Users implements Serializable{
    private Integer id;
    private String name;

    @Override
    public String toString(){
        return "{id:" + id + ",name:" + name + "}";
    }
}
