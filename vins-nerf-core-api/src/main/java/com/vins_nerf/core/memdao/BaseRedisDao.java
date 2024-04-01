package com.vins_nerf.core.memdao;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BaseRedisDao {
    /**
     * 关闭redis连接池
     *
     * @since Ver 3.0
     */
    void poolClose();

    /**
     * 初始化连接池
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param timeout
     * @param maxIdle
     * @param maxTotal
     * @since Ver 3.0
     */
    void init(String host, int port, String username, String password, int timeout, int maxIdle, int maxTotal);

    /**
     * 根据key判断是否存在
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    boolean isKeyExists(String key);

    /**
     * 根据key获取所有的filed和对应的值
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    Map<String, String> getHashAll(String key);

    /**
     * 根据Key,field查询
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    String getHashString(String key, String field);

    /**
     * 根据key和多个field查询
     *
     * @param key
     * @param fields
     * @return
     * @since Ver 3.0
     */
    List<String> getHashString(String key, String... fields);

    /**
     * 设置key的filed和对应的值
     *
     * @param key
     * @param value
     * @return
     * @since Ver 3.0
     */
    String setHashString(String key, Map<String, String> value);

    /**
     * 设置key的filed和对应的值
     *
     * @param key
     * @param value
     * @return
     * @since Ver 3.0
     */
    String setHashString(String key, Map<String, String> value, long seconds);

    /**
     * 设置key的filed和对应的值
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    long setHashString(String key, String field, String value);

    /**
     * 设置hash值
     *
     * @param key
     * @param field
     * @param value
     * @return
     * @since Ver 3.0
     */
    long setHashString(String key, String field, String value, long seconds);

    /**
     * 删除key的field对应的hash值
     *
     * @param key
     * @param field
     * @return
     * @since Ver 3.0
     */
    long delHashString(String key, String field);

    /**
     * 设置string
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     * @since Ver 3.0
     */
    String setKeyString(String key, String value, long seconds);

    /**
     * 获取String
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    String getKeyString(String key);

    /**
     * 删除String
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    long delKeyString(String key);

    /**
     * incr HashString
     *
     * @param key
     * @param field
     * @param incrValue
     * @return
     */
    long incrHashString(String key, String field, long incrValue);

    /**
     * 根据key设置存活时间
     *
     * @param key
     * @param sec
     * @return
     * @since Ver 3.0
     */
    long setExpireByKey(String key, long sec);

    /**
     * 判断hash的key和field是否存在
     *
     * @param key
     * @param field
     * @return
     * @since Ver 3.0
     */
    boolean isHashKeyExists(String key, String field);

    /**
     * 原子性增加
     *
     * @param key
     * @return
     */
    long incrString(String key);

    /**
     * getset
     *
     * @param key
     * @param value
     * @param sec
     * @return
     */
    String getset(String key, String value, long sec);

    /**
     * set the value of a key, only if the key is not exists.
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    Long setIfNotExists(String key, String value);

    /**
     * set the value of a key, only if the key is not exists.
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    Long setIfNotExists(String key, String value, long sec);

    /**
     * add the member into set.
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    Long sadd(String key, long sec, String... value);

    /**
     * check the member set.
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    boolean sismember(String key, String value);

    /**
     * get the size of set.
     *
     * @param key
     * @return
     * @since Ver 3.0
     */
    Long scard(String key);

    /**
     * get keys, which match pattern.
     *
     * @param pattern
     * @return
     */
    Set<String> getKeys(String pattern);

    /**
     * delete keys, which match pattern.
     *
     * @param pattern
     * @return
     */
    long delKeys(String pattern);
}
