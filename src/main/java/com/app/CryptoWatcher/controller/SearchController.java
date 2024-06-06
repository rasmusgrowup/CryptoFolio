package com.app.CryptoWatcher.controller;

import com.app.CryptoWatcher.model.CryptoCurrency;
import com.app.CryptoWatcher.service.CryptoApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class SearchController {
    private final CryptoApiService cryptoApiService;

    public SearchController(CryptoApiService cryptoApiService) {
        this.cryptoApiService = cryptoApiService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchQuery") String searchQuery, Model model) {
        List<CryptoCurrency> searchResults = cryptoApiService.searchCryptoCurrencies(searchQuery);
        model.addAttribute("searchResults", searchResults);
        return "searchpage";
    }
}
