package com.app.CryptoFolio.controller;

import com.app.CryptoFolio.model.CryptoCurrency;
import com.app.CryptoFolio.service.CryptoApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
