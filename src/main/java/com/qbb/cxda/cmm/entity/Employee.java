package com.qbb.cxda.cmm.entity;

import com.qbb.cxda.constant.BasecConstant;
import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    private Integer id;
    private String name;
    private String identityId;
    private String picUrls;
    private String describes;
    private String comment;
    private Byte checkStatus;
    private Byte isDelete;
    private Integer creater;
    private Date createTime;
    private Date updateTime;

    public Employee(){
        this.isDelete = BasecConstant.IS_DELETE_NO;
    }

    public Employee(int id){
        this.id = id;
    }

    public Employee(String name,String identityId){
        this.name = name;
        this.identityId = identityId;
        this.isDelete = BasecConstant.IS_DELETE_NO;
    }

    public Employee(String name,Byte checkStatus){
        this.name = name;
        this.checkStatus = checkStatus;
        this.isDelete = BasecConstant.IS_DELETE_NO;
    }

}