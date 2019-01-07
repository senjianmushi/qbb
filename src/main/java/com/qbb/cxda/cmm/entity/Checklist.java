package com.qbb.cxda.cmm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Checklist {
    private Integer id;
    private Byte type;
    private String comment;
    private Integer checkId;
    private Byte checkStatus;
    private Date checkDate;
    private Byte isDelete;
    private Integer creater;
    private Date createTime;
    private Date updateTime;
}