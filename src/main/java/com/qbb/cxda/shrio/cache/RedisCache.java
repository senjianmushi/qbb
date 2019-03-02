package com.qbb.cxda.shrio.cache;

import com.qbb.cxda.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisCache<k,v> implements Cache<k,v> {

    private final String CACHE_PREFIX = "crrchz-cache";

    @Autowired
    private JedisUtil jedisUtil;

    private  byte[] getKey(k k){
//        System.out.println("从redis获取缓存数据");
        if(k instanceof  String ){
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public v get(k k) throws CacheException {
        byte[] value = jedisUtil.get(getKey(k));
        if(value != null ){
            return (v)SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public v put(k k, v v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    @Override
    public v remove(k k) throws CacheException {
//        System.out.println("redis删除缓存数据");
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if(value != null){
            return (v)SerializationUtils.deserialize(value);
        }
        return null;
    }

    /**
     * 不要轻易去清楚，会影响到redis存储的其他值
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<k> keys() {
        return null;
    }

    @Override
    public Collection<v> values() {
        return null;
    }
}
