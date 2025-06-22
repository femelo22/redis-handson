package br.com.lfmelo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedisCacheServiceImpl {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Salva um objeto no cache sem TTL
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Salva um objeto no cache com TTL
     */
    public void setWithTTL(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Obtém um objeto do cache
     */
    public <T> Optional<T> get(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null && type.isInstance(value)) {
            return Optional.of((T) value);
        }
        return Optional.empty();
    }

    /**
     * Remove uma chave do cache
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Define um TTL para uma chave existente
     */
    public void expire(String key, Duration ttl) {
        redisTemplate.expire(key, ttl);
    }



}
