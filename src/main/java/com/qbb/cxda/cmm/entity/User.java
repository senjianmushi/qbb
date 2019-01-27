package com.qbb.cxda.cmm.entity;

import com.qbb.cxda.constant.BasecConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String docUrl;
    private String email;
    private String telephone;
    private Byte type;
    private Byte checkStatus;
    private Byte isDelete;
    private Date createTime;
    private Date updateTime;

    public User(){}
    public User(Integer userId){
       this.id = userId;
    }
    public User(String name,Byte checkStatus){
        this.name = name;
        this.checkStatus = checkStatus;
        this.isDelete = BasecConstant.IS_DELETE_NO;
        this.type = BasecConstant.TYPE_NOMAL;
    }
    public User(String username,String password){
        this.username = username;
        this.password = password;
        this.isDelete = BasecConstant.IS_DELETE_NO;
    }
    public User(String username){
        this.username = username;
        this.isDelete = BasecConstant.IS_DELETE_NO;
    }
}