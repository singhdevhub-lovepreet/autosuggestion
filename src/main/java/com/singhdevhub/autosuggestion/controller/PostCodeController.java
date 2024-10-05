package com.singhdevhub.autosuggestion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singhdevhub.autosuggestion.commons.MessageRequest;
import com.singhdevhub.autosuggestion.service.TrieService;
import com.singhdevhub.autosuggestion.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("autosuggestion/")
public class PostCodeController
{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TrieService trieService;

    @PostMapping(value = "/postCode")
    public void handleMessage(@RequestBody MessageRequest request)
    {
        String code = request.getCode();
        String userId = request.getUserId();
        if(ValidationUtil.validParentheses(code)){
            trieService.createAndSaveTrie(code, userId);
        }
    }

}
