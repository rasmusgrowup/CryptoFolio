package com.app.CryptoWatcher.service;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.model.CryptoCurrency;

import java.util.List;

public interface CryptoCurrencyService {
    CryptoCurrency addCryptoCurrencyToPortfolio(String id);

    CryptoCurrency removeCryptoCurrencyFromPortfolio(String id);

    CryptoCurrencyDTO getCryptoCurrencyByName(String name);
    List<CryptoCurrencyDTO> getAllCryptoCurrencies();

    String getCurrencyIds();

    void updateCryptoCurrency(String id);
}
