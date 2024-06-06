package com.app.CryptoFolio.util;

import com.app.CryptoFolio.model.CryptoCurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrencyListResponse {
    private CryptoCurrency[] data;
}

