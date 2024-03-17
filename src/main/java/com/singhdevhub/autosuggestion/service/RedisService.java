package com.singhdevhub.autosuggestion.service;

import com.singhdevhub.autosuggestion.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService
{
    @Autowired
    private RedisTemplate<String, Trie> redisTemplate;

    public Trie getDataFromRedis(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void updateDataInRedis(String key, Trie data) {
        redisTemplate.opsForValue().set(key, data);
    }

}
