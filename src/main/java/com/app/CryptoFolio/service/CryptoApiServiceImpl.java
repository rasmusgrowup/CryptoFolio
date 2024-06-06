package com.app.CryptoFolio.service;

import com.app.CryptoFolio.DTO.CryptoCurrencyDTO;
import com.app.CryptoFolio.model.CryptoCurrency;
import com.app.CryptoFolio.util.CryptoCurrencyListResponse;
import com.app.CryptoFolio.util.CryptoCurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CryptoApiServiceImpl implements CryptoApiService {
    @Value("${coincap.api.url}")
    private String COINCAP_API_URL;
    private final RestTemplate restTemplate;

    @Autowired
    public CryptoApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CryptoCurrencyDTO> getAllCryptoCurrencies() {
        String url = COINCAP_API_URL + "/assets";
        CryptoCurrencyListResponse response = restTemplate.getForObject(url, CryptoCurrencyListResponse.class);
        if (response != null && response.getData() != null) {
            return mapToList(response.getData());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public CryptoCurrency getCryptoCurrencyDetails(String id) {
        String url = COINCAP_API_URL + "/assets/" + id;
        CryptoCurrencyResponse response = restTemplate.getForObject(url, CryptoCurrencyResponse.class);
        return response != null ? response.getData() : null;
    }

    @Override
    public CryptoCurrencyDTO getCryptoCurrencyDTODetails(String id) {
        String url = COINCAP_API_URL + "/assets/" + id;
        CryptoCurrencyResponse response = restTemplate.getForObject(url, CryptoCurrencyResponse.class);
        return response != null ? mapToDTO(response.getData()) : null;
    }


    @Override
    public List<CryptoCurrency> searchCryptoCurrencies(String search) {
        // Construct the URL with the search parameter
        String url = COINCAP_API_URL + "/assets?search=" + search;

        // Make a GET request to the API endpoint
        CryptoCurrencyListResponse response = restTemplate.getForObject(url, CryptoCurrencyListResponse.class);

        // Extract the array of currencies from the response
        if (response != null && response.getData() != null) {
            return Arrays.asList(response.getData());
        } else {
            return Collections.emptyList();
        }
    }

    private List<CryptoCurrencyDTO> mapToList(CryptoCurrency[] data) {
        return Arrays.stream(data)
                .map(this::mapToDTO)
                .toList();
    }

    private CryptoCurrencyDTO mapToDTO(CryptoCurrency data) {
        return CryptoCurrencyDTO.builder()
                .id(data.getId())
                .rank(data.getRank())
                .symbol(data.getSymbol())
                .name(data.getName())
                .supply(data.getSupply())
                .maxSupply(data.getMaxSupply())
                .marketCapUsd(data.getMarketCapUsd())
                .volumeUsd24Hr(data.getVolumeUsd24Hr())
                .priceUsd(data.getPriceUsd())
                .changePercent24Hr(data.getChangePercent24Hr())
                .vwap24Hr(data.getVwap24Hr())
                .explorer(data.getExplorer())
                .build();
    }
}
