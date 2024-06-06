package com.app.CryptoWatcher.controller;

import com.app.CryptoWatcher.model.CryptoCurrency;
import com.app.CryptoWatcher.service.CryptoCurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormController {
    private final CryptoCurrencyService cryptoCurrencyService;

    public FormController(CryptoCurrencyService cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @PostMapping("/add")
    public String addToPortfolio(@RequestParam("id") String cryptoCurrencyId, RedirectAttributes redirectAttributes) {
        // Call portfolioService.addCryptoCurrencyToPortfolio with the id
        CryptoCurrency cryptoCurrency = cryptoCurrencyService.addCryptoCurrencyToPortfolio(cryptoCurrencyId);

        // Check if the addition was successful
        if (cryptoCurrency != null) {
            // Redirect back to the search page with a success message
            redirectAttributes.addFlashAttribute("successMessage", "Cryptocurrency added to portfolio successfully");
        } else {
            // If addition fails, redirect back to the search page with an error message
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add cryptocurrency to portfolio");
        }
        return "redirect:/";
    }

    @PostMapping("/remove")
    public String removeFromPortfolio(@RequestParam("id") String cryptoCurrencyId, RedirectAttributes redirectAttributes) {
        // Call portfolioService.addCryptoCurrencyToPortfolio with the id
        CryptoCurrency cryptoCurrency = cryptoCurrencyService.removeCryptoCurrencyFromPortfolio(cryptoCurrencyId);

        // Check if the addition was successful
        if (cryptoCurrency != null) {
            // Redirect back to the search page with a success message
            redirectAttributes.addFlashAttribute("successMessage", "Cryptocurrency added to portfolio successfully");
        } else {
            // If addition fails, redirect back to the search page with an error message
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add cryptocurrency to portfolio");
        }

        return "redirect:/";
    }
}