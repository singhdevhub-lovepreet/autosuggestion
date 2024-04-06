package com.singhdevhub.autosuggestion.controller;

import com.singhdevhub.autosuggestion.service.RedisService;
import com.singhdevhub.autosuggestion.service.TrieService;
import com.singhdevhub.autosuggestion.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SuggestionController
{
    @Autowired
    private TrieService trieService;

    @Autowired
    private RedisService redisService;

    @PostMapping(path = "/v1/suggest")
    public ResponseEntity<String> getSuggestion(@PathVariable(value = "userId") String userId, @RequestBody String code){
        try{
            List<String> words = trieService.getWords(code);
            String response = trieService.find(words, redisService.getDataFromRedis(userId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in trie service", HttpStatus.BAD_REQUEST);
        }
    }



}
