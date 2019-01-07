package com.qbb.cxda.base;
/**
 * 基础枚举接口
 */
public interface BaseEnum<K, V> {

    /**
     * 获取编码
     */
    K code();

    /**
     * 获取描述信息
     */
    V msg();
}
