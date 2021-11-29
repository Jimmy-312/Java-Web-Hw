package com.project.javaweb.pojo;

import java.io.Serializable;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

@Data
public class Files implements Serializable{
    private Integer id;
    private String name;
    private String type;
    private String tag;
    private Integer ownerid;
    private DateTime createtime;
    private DateTime updatetime;
    private String ispublic;
    private Integer srcid;
}
