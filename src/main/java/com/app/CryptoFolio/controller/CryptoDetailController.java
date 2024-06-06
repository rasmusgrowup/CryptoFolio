package com.app.CryptoFolio.controller;

import com.app.CryptoFolio.DTO.CryptoCurrencyDTO;
import com.app.CryptoFolio.service.CryptoApiService;
import com.app.CryptoFolio.service.CryptoCurrencyService;
import com.app.CryptoFolio.service.WebSocketClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CryptoDetailController {
    private final CryptoCurrencyService cryptoCurrencyService;
    private final WebSocketClientService webSocketClientService;
    private final CryptoApiService cryptoApiService;

    public CryptoDetailController(CryptoCurrencyService cryptoCurrencyService, WebSocketClientService webSocketClientService, CryptoApiService cryptoApiService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.webSocketClientService = webSocketClientService;
        this.cryptoApiService = cryptoApiService;
    }

    @GetMapping("/crypto/{id}")
    public String showCryptoDetail(@PathVariable("id") String id, Model model) {
        CryptoCurrencyDTO cryptoCurrency = cryptoApiService.getCryptoCurrencyDTODetails(id);
        List<CryptoCurrencyDTO> updatedCryptoCurrencies = cryptoCurrencyService.getAllCryptoCurrencies();
        boolean containsCryptoCurrency = updatedCryptoCurrencies.stream()
                .anyMatch(crypto -> crypto.getId().equals(cryptoCurrency.getId()));
        model.addAttribute("cryptoCurrencies", updatedCryptoCurrencies);
        model.addAttribute("cryptoCurrency", cryptoCurrency);
        model.addAttribute("containsCryptoCurrency", containsCryptoCurrency);
        webSocketClientService.connectToPriceUpdates(id);
        return "crypto-detail";
    }
}
