package com.app.CryptoFolio.service;

import com.app.CryptoFolio.DTO.CryptoCurrencyDTO;
import com.app.CryptoFolio.model.CryptoCurrency;

import java.util.List;

public interface CryptoApiService {
    List<CryptoCurrencyDTO> getAllCryptoCurrencies();
    CryptoCurrency getCryptoCurrencyDetails(String id);

    CryptoCurrencyDTO getCryptoCurrencyDTODetails(String id);

    List<CryptoCurrency> searchCryptoCurrencies(String searchQuery);
}
