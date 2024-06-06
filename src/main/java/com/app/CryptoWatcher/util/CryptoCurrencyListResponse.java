package com.app.CryptoWatcher.util;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.model.CryptoCurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrencyListResponse {
    private CryptoCurrency[] data;
}

