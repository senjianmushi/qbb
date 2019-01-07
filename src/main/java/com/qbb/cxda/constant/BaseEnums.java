package com.qbb.cxda.constant;

import com.qbb.cxda.base.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础枚举值
 */
public enum BaseEnums implements BaseEnum<Integer, String> {

    SUCCESS(0, "success"),
    PARAM_ERROR(10000, "parameter error"),//10000-20000是用户参数的校验出错
    WRONG_PASSWORD(10001, "the password is wrong"),//密码错误
    SESSION_INVALID(10002, "session is invalid"),//session无效
    SESSION_EXPIRED(10003, "session is expired"),//session过期
    SESSION_EXIST(10004, "session exist"),//session已存在，账号登录中
    USER_NAME_REPEATED(1005, "user name repeated"),// 用户名重复
    NO_SUCH_USER(1006, "no such user or was deleted"),// 没有该用户或者已被删除
    UNEXPECTED_ERROR(1007, "unexpected error"),// 未知错误

    /**
     * 候选人相关的11000-12000
     */
    NO_SUCH_EMPLOYEE(11000, "no such news or was deleted"),// 没有该候选人消息

    /**
     * 12000-12099:评论(字符串)错误码
     */
    EMPTY_MESSAGE(12000, "message is empty"),// 评论内容为空
    LONG_MESSAGE(12001, "message is too long(less than 512 byte)"),// 评论内容超长
    LONG_USER_ID(12002, "user id is too long(less than 64 byte)"),// 用户id超长
    LONG_USER_NAME(12003, "user name is too long(less than 128 byte)");// 用户名超长

    private int code;
    private String msg;

    private static Map<Integer, String> allMap = new HashMap<>();
    BaseEnums() {}

    BaseEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        for(BaseEnums enums : BaseEnums.values()){
            allMap.put(enums.code, enums.msg);
        }
    }

    public String msg(String code) {
        return allMap.get(code);
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
