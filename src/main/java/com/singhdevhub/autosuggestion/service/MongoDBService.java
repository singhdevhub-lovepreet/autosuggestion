package com.singhdevhub.autosuggestion.service;

import com.singhdevhub.autosuggestion.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService
{

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveData(String key, Trie data) {
        mongoTemplate.save(data, key);
    }

    public Trie getData(String userId) {
        return mongoTemplate.findById(userId, Trie.class);
    }

}
