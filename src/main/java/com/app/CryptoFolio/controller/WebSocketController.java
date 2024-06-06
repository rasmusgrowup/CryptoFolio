package com.app.CryptoFolio.controller;

import com.app.CryptoFolio.service.WebSocketClientService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class WebSocketController extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketClientService webSocketClientService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, WebSocketClientService webSocketClientService) {
        this.messagingTemplate = messagingTemplate;
        this.webSocketClientService = webSocketClientService;
    }

    @MessageMapping("/update")
    public void updateCurrency(String id) {
        webSocketClientService.handleUpdateRequest(id);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        messagingTemplate.convertAndSend("/topic/prices", payload);
    }
}