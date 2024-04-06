package com.singhdevhub.autosuggestion.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singhdevhub.autosuggestion.commons.MessageRequest;
import com.singhdevhub.autosuggestion.service.TrieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class WebsocketHandler extends TextWebSocketHandler
{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TrieService trieService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws IOException
    {
        MessageRequest messageRequest = objectMapper.convertValue(message.getPayload(), MessageRequest.class);
        String code = messageRequest.getCode();
        String userId = messageRequest.getUserId();
        trieService.createAndSaveTrie(code, userId);
        session.sendMessage(new TextMessage("Message Saved....."));
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection is established......");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection is closed......");
    }

}
