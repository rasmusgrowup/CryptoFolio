package com.app.CryptoWatcher.service;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.model.CryptoCurrency;

import java.util.List;

public interface CryptoApiService {
    List<CryptoCurrencyDTO> getAllCryptoCurrencies();
    CryptoCurrency getCryptoCurrencyDetails(String id);

    CryptoCurrencyDTO getCryptoCurrencyDTODetails(String id);

    List<CryptoCurrency> searchCryptoCurrencies(String searchQuery);
}
