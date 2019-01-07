package com.qbb.cxda.cmm.response;

import com.qbb.cxda.cmm.entity.Employee;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeListResponnse {

    private Integer id;
    private String name;
    private String identityId;
    private String describes;
    private String comment;
    private String[] picUrls;
    private Byte checkStatus;
    private Integer creater;
    private Date createTime;

    public EmployeeListResponnse(){}

    public EmployeeListResponnse(Employee employee,String prixPath){
        this.id  = employee.getId();
        this.name = employee.getName();
        this.identityId = employee.getIdentityId();
        this.describes = employee.getDescribes();
        this.comment = employee.getComment();
        this.picUrls = employee.getPicUrls().split(";");
        for(int i = 0;i < picUrls.length;i++){
            picUrls[i] = prixPath + picUrls[i];
        }
        this.checkStatus = employee.getCheckStatus();
        this.creater = employee.getCreater();
        this.createTime = employee.getCreateTime();
    }

}
