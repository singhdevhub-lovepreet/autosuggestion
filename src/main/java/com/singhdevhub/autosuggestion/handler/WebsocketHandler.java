package com.singhdevhub.autosuggestion.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singhdevhub.autosuggestion.commons.MessageRequest;
import com.singhdevhub.autosuggestion.service.TrieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Controller
public class WebsocketHandler
{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TrieService trieService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws IOException
    {
        MessageRequest messageRequest = objectMapper.convertValue(message.getPayload(), MessageRequest.class);
        String code = messageRequest.getCode();
        String userId = messageRequest.getUserId();
        trieService.createAndSaveTrie(code, userId);
        session.sendMessage(new TextMessage("Message Saved....."));
    }

}
