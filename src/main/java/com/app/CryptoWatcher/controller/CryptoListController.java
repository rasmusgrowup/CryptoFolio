package com.app.CryptoWatcher.controller;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.service.CryptoApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CryptoListController {
    private final CryptoApiService cryptoApi;

    public CryptoListController(CryptoApiService cryptoCurrencyService) {
        this.cryptoApi = cryptoCurrencyService;
    }

    @GetMapping("/crypto")
    public String showCryptoList(Model model) {
        List<CryptoCurrencyDTO> cryptoCurrencies = cryptoApi.getAllCryptoCurrencies();
        model.addAttribute("cryptoCurrencies", cryptoCurrencies);
        return "crypto-list";
    }
}
