package com.qbb.cxda.constant;

public interface BasecConstant {

    public static final Byte CHECK_STATUS_UNCHECK = 0;//待审核
    public static final Byte CHECK_STATUS_SUCCESS = 1;//审核通过
    public static final Byte CHECK_STATUS_FAIL = -1;//审核不通过
    public static final Byte CHECK_STATUS_AGAIN = 2;//审核通过

    public static Byte IS_DELETE_NO = 0;//未删除
    public static Byte IS_DELETE_YES = 1;//删除

    public static Byte TYPE_NOMAL = 0;//未删除
    public static Byte TYPE_MANAGEER = 1;//删除


}
