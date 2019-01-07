package com.qbb.cxda.base;

import com.qbb.cxda.constant.BaseEnums;
import lombok.Data;

@Data
public class ResultObject {

    private int code;//返回状态码，为空则默认200.前端需要拦截一些常见的状态码如403、404、500等
    private String msg;//编码，可用于前端处理多语言，不需要则不用返回编码
    private Object data = null;//相关消息

    public ResultObject(){
    }

    public ResultObject(BaseEnums result){
        this.code = result.code();
        this.msg = result.msg();
        if(this.code == 0){
            this.data = this.msg;
        }
    }

    public ResultObject(BaseEnums result, Object data){
        this.code = result.code();
        this.msg = result.msg();
        if(data != null){
            //this.value = value.toString();
            this.data = data;
        }
    }
}
