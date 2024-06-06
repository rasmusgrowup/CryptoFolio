package com.app.CryptoWatcher.controller;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.service.CryptoCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class FrontController {
    private final CryptoCurrencyService cryptoCurrencyService;

    @Autowired
    public FrontController(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @GetMapping("/")
    public String showFrontPage(Model model) {
        List<CryptoCurrencyDTO> updatedCryptoCurrencies = cryptoCurrencyService.getAllCryptoCurrencies();
        model.addAttribute("cryptoCurrencies", updatedCryptoCurrencies);
        return "frontpage";
    }

//    @SubscribeMapping("/topic/updates")
//    public String showUpdatedFrontPage(Model model) {
//        List<CryptoCurrencyDTO> updatedCryptoCurrencies = cryptoCurrencyService.getAllCryptoCurrencies();
//        model.addAttribute("cryptoCurrencies", updatedCryptoCurrencies);
//        System.out.println("Updated frontpage");
//        return "frontpage";
//    }
}
