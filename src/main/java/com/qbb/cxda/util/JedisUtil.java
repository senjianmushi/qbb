package com.qbb.cxda.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.Set;

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }

    /**
     * 插入数据
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key ,byte[] value){
        Jedis jedis = getResource();
        try {
            jedis.set(key,value);
            return value;
        }finally {
            jedis.close();
        }
    }

    /**
     * 设置超时时间
     * @param key
     * @param i
     */
    public void expire(byte[] key ,int i){
        Jedis jedis = getResource();
        try{
            jedis.expire(key,i);
        }finally {
            jedis.close();
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public byte[] get(byte[] key){
        Jedis jedis = getResource();
        try {
            return  jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    /**
     * 删除key
     * @param key
     */
    public void del(byte[] key){
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shrio_session_prefix){
        Jedis jedis = getResource();
        try {
            return jedis.keys((shrio_session_prefix + "*").getBytes());
        }finally {
            jedis.close();
        }
    }

}
