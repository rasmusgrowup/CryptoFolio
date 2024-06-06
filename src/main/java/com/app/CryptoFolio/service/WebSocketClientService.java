package com.app.CryptoFolio.service;

import com.app.CryptoFolio.DTO.CryptoCurrencyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

@Service
public class WebSocketClientService {
    private final CryptoCurrencyService cryptoCurrencyService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketClientService(CryptoCurrencyService cryptoCurrencyService, SimpMessagingTemplate messagingTemplate) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.messagingTemplate = messagingTemplate;
    }

    public void handleUpdateRequest(String id) {
        cryptoCurrencyService.updateCryptoCurrency(id);
        // Fetch the updated cryptocurrency
        CryptoCurrencyDTO updatedCryptoCurrency = cryptoCurrencyService.getCryptoCurrencyByName(id);

        // Convert the updated cryptocurrency to JSON
        String updatedCryptoCurrencyJson = convertToJson(updatedCryptoCurrency);

        // Send the updated cryptocurrency to the "/topic/updates" topic
        messagingTemplate.convertAndSend("/topic/updates", updatedCryptoCurrencyJson);

        System.out.println("Updated currency with id: " + id);
    }

    @PostConstruct
    public void connect() {
        String ids = cryptoCurrencyService.getCurrencyIds();
        String wsUrl = "wss://ws.coincap.io/prices?assets=" + ids;

        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                messagingTemplate.convertAndSend("/topic/prices", message.getPayload());
            }
        }, wsUrl);
    }

    public void connectToPriceUpdates(String id) {
        String wsUrl = "wss://ws.coincap.io/prices?assets=" + id;

        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(new TextWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                // Parse the incoming message to extract the price data
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> priceData = mapper.readValue(message.getPayload(), new TypeReference<Map<String, String>>(){});

                // Send the price data to the "/topic/price/{id}" topic
                messagingTemplate.convertAndSend("/topic/price/" + id, priceData.get(id));
            }
        }, wsUrl);
    }

    private String convertToJson(CryptoCurrencyDTO cryptoCurrency) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(cryptoCurrency);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }
}
