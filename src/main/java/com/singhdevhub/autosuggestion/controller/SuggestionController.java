package com.singhdevhub.autosuggestion.controller;

import com.singhdevhub.autosuggestion.commons.MessageRequest;
import com.singhdevhub.autosuggestion.service.RedisService;
import com.singhdevhub.autosuggestion.service.TrieService;
import com.singhdevhub.autosuggestion.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autosuggestion/")
public class SuggestionController
{
    @Autowired
    private TrieService trieService;

    @Autowired
    private RedisService redisService;

    @PostMapping(path = "/v1/suggest") // call on hitting space
    public ResponseEntity<String> getSuggestion(@RequestBody MessageRequest request){
        try{
            List<String> words = trieService.getWords(request.getCode());
            String response = trieService.find(words, redisService.getDataFromRedis(request.getUserId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in trie service", HttpStatus.BAD_REQUEST);
        }
    }

}
