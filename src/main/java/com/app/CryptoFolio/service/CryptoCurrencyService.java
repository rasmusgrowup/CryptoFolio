package com.app.CryptoFolio.service;

import com.app.CryptoFolio.DTO.CryptoCurrencyDTO;
import com.app.CryptoFolio.model.CryptoCurrency;

import java.util.List;

public interface CryptoCurrencyService {
    CryptoCurrency addCryptoCurrencyToPortfolio(String id);

    CryptoCurrency removeCryptoCurrencyFromPortfolio(String id);

    CryptoCurrencyDTO getCryptoCurrencyByName(String name);
    List<CryptoCurrencyDTO> getAllCryptoCurrencies();

    String getCurrencyIds();

    void updateCryptoCurrency(String id);
}
