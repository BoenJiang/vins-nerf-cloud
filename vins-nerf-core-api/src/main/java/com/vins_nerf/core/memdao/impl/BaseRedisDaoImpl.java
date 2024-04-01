package com.vins_nerf.core.memdao.impl;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.core.memdao.BaseRedisDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@Slf4j
public abstract class BaseRedisDaoImpl implements BaseRedisDao {
    protected static final String OK = "OK";
    private final AtomicInteger errorNum = new AtomicInteger();
    private final Lock lock = new ReentrantLock();
    protected JedisPool pool;
    private volatile boolean isStarting = false;

    public abstract void init();

    protected void addErrorNum() {
        int error = errorNum.getAndIncrement();

        if (error > 0 && error % 10 == 0 && !isStarting) { // 累计错误每超过10次，进行重新初始化
            init();
        }

    }

    @Override
    public void init(String host, int port, String username, String password, int timeout, int maxIdle, int maxTotal) {
        log.info(this.getClass().getName() + " redis init...");
        this.getLock().lock();
        try {
            this.setStarting(true);
            if (pool != null) {
                pool.destroy();
                pool = null;
            }
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setTestOnBorrow(false);
            config.setTestOnReturn(false);
            password = StringUtil.isNullOrEmpty(password) ? null : password;
            pool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        } catch (Exception e) {
            log.error("init RedisPool error ===>", e);
            if (pool != null) {
                pool.destroy();
                pool = null;
            }
        } finally {
            this.setStarting(false);
            this.getLock().unlock();
        }
    }

    @Override
    public void poolClose() {
        if (pool != null) {
            pool.close();
        }
    }

    @Override
    public boolean isKeyExists(String key) {
        boolean result = false;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public Map<String, String> getHashAll(String key) {
        Map<String, String> result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String getHashString(String key, String field) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public List<String> getHashString(String key, String... fields) {
        List<String> result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hmget(key, fields);
        } catch (Exception e) {
            log.info("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String setHashString(String key, Map<String, String> value) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hmset(key, value);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String setHashString(String key, Map<String, String> value, long sec) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            Transaction transaction = jedis.multi();
            transaction.hmset(key, value);
            transaction.expire(key, sec);
            List<Object> results = transaction.exec();
            if (results != null && !results.isEmpty()) {
                result = results.get(0).toString();
            }
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long setHashString(String key, String field, String value) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String setKeyString(String key, String value, long seconds) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long setHashString(String key, String field, String value, long seconds) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hset(key, field, value);
            if (seconds > 0) jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long delHashString(String key, String field) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hdel(key, field);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String getKeyString(String key) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long delKeyString(String key) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long incrHashString(String key, String field, long incrValue) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hincrBy(key, field, incrValue);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long incrString(String key) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.incr(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long setExpireByKey(String key, long sec) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.expire(key, sec);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public boolean isHashKeyExists(String key, String field) {
        boolean result = false;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.hexists(key, field);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public String getset(String key, String value, long sec) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            Transaction transaction = jedis.multi();
            transaction.getSet(key, value);
            transaction.expire(key, sec);
            List<Object> results = transaction.exec();
            if (results != null && !results.isEmpty()) {
                Object object0 = results.get(0);
                result = object0 == null ? null : object0.toString();
            }
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public Long setIfNotExists(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
            return 1L;
        }
    }

    @Override
    public Long setIfNotExists(String key, String value, long sec) {
        Long result = null;
        try (Jedis jedis = pool.getResource()) {

            Transaction transaction = jedis.multi();
            transaction.setnx(key, value);
            transaction.expire(key, sec);
            List<Object> results = transaction.exec();
            if (results != null && !results.isEmpty()) {
                result = Long.parseLong(results.get(0).toString());
            }
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public Long sadd(String key, long sec, String... values) {
        if (values == null || values.length == 0) return 0L;
        Set<String> valueSet = new HashSet<>();
        for (String value : values) {
            if (!StringUtil.isNullOrEmpty(value)) {
                valueSet.add(value);
            }
        }
        values = valueSet.toArray(values);
        if (values == null || values.length == 0) return 0L;

        Long result = null;
        try (Jedis jedis = pool.getResource()) {

            Transaction transaction = jedis.multi();
            transaction.sadd(key, values);
            transaction.expire(key, sec);
            List<Object> results = transaction.exec();
            if (results != null && !results.isEmpty()) {
                result = Long.parseLong(results.get(0).toString());
            }
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public boolean sismember(String key, String value) {
        boolean result = false;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public Long scard(String key) {
        Long result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.scard(key);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        Set<String> result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

    @Override
    public long delKeys(String pattern) {
        long result = 0;
        try (Jedis jedis = pool.getResource()) {
            Set<String> keys = jedis.keys(pattern);
            result = keys == null || keys.isEmpty() ? 0 : jedis.del(keys.toArray(new String[]{}));
        } catch (Exception e) {
            log.error("Opt redis error:", e);
            this.addErrorNum();
        }
        return result;
    }

}
