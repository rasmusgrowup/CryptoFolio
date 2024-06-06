package com.app.CryptoWatcher.service;

import com.app.CryptoWatcher.DTO.CryptoCurrencyDTO;
import com.app.CryptoWatcher.model.CryptoCurrency;
import com.app.CryptoWatcher.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoApiServiceImpl cryptoApiService;

    public CryptoCurrencyServiceImpl(CryptoCurrencyRepository cryptoCurrencyRepository, CryptoApiServiceImpl cryptoApiService) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.cryptoApiService = cryptoApiService;
    }

    @Override
    public CryptoCurrency addCryptoCurrencyToPortfolio(String id) {
        Optional<CryptoCurrency> cryptoCurrencyOptional = cryptoCurrencyRepository.findById(id);
        if (cryptoCurrencyOptional.isPresent()) {
            return cryptoCurrencyOptional.get();
        } else {
            CryptoCurrency currencyDetails = cryptoApiService.getCryptoCurrencyDetails(id);
            currencyDetails = CryptoCurrency.builder()
                    .id(currencyDetails.getId())
                    .rank(currencyDetails.getRank())
                    .symbol(currencyDetails.getSymbol())
                    .name(currencyDetails.getName())
                    .supply(currencyDetails.getSupply())
                    .maxSupply(currencyDetails.getMaxSupply())
                    .marketCapUsd(currencyDetails.getMarketCapUsd())
                    .volumeUsd24Hr(currencyDetails.getVolumeUsd24Hr())
                    .priceUsd(currencyDetails.getPriceUsd())
                    .changePercent24Hr(currencyDetails.getChangePercent24Hr())
                    .vwap24Hr(currencyDetails.getVwap24Hr())
                    .explorer(currencyDetails.getExplorer())
                    .build();
            return cryptoCurrencyRepository.save(currencyDetails);
        }
    }

    @Override
    public CryptoCurrency removeCryptoCurrencyFromPortfolio(String id) {
        Optional<CryptoCurrency> cryptoCurrencyOptional = cryptoCurrencyRepository.findById(id);
        if (cryptoCurrencyOptional.isPresent()) {
            CryptoCurrency currency = cryptoCurrencyOptional.get();
            cryptoCurrencyRepository.delete(currency);
            return currency;
        }
        return null;
    }

    @Override
    public CryptoCurrencyDTO getCryptoCurrencyByName(String name) {
        Optional<CryptoCurrency> cryptoCurrency = cryptoCurrencyRepository.findById(name);
        return cryptoCurrency.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<CryptoCurrencyDTO> getAllCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findAll();
        return cryptoCurrencies.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public String getCurrencyIds() {
        List<CryptoCurrency> currencies = cryptoCurrencyRepository.findAll();
        return currencies.stream()
                .map(CryptoCurrency::getId)
                .collect(Collectors.joining(","));
    }

    @Override
    public void updateCryptoCurrency(String id) {
        CryptoCurrency updatedCurrency = cryptoApiService.getCryptoCurrencyDetails(id);
        CryptoCurrency existingCurrency = cryptoCurrencyRepository.findById(id).orElse(null);

        if (existingCurrency != null) {
            existingCurrency.setRank(updatedCurrency.getRank());
            existingCurrency.setSymbol(updatedCurrency.getSymbol());
            existingCurrency.setName(updatedCurrency.getName());
            existingCurrency.setSupply(updatedCurrency.getSupply());
            existingCurrency.setMaxSupply(updatedCurrency.getMaxSupply());
            existingCurrency.setMarketCapUsd(updatedCurrency.getMarketCapUsd());
            existingCurrency.setVolumeUsd24Hr(updatedCurrency.getVolumeUsd24Hr());
            existingCurrency.setPriceUsd(updatedCurrency.getPriceUsd());
            existingCurrency.setChangePercent24Hr(updatedCurrency.getChangePercent24Hr());
            existingCurrency.setVwap24Hr(updatedCurrency.getVwap24Hr());
            existingCurrency.setExplorer(updatedCurrency.getExplorer());
            cryptoCurrencyRepository.save(existingCurrency);
        }
    }

    public CryptoCurrencyDTO mapToDTO(CryptoCurrency cryptoCurrency) {
        return CryptoCurrencyDTO.builder()
                .id(cryptoCurrency.getId())
                .rank(cryptoCurrency.getRank())
                .symbol(cryptoCurrency.getSymbol())
                .name(cryptoCurrency.getName())
                .supply(cryptoCurrency.getSupply())
                .maxSupply(cryptoCurrency.getMaxSupply())
                .marketCapUsd(cryptoCurrency.getMarketCapUsd())
                .volumeUsd24Hr(cryptoCurrency.getVolumeUsd24Hr())
                .priceUsd(cryptoCurrency.getPriceUsd())
                .changePercent24Hr(cryptoCurrency.getChangePercent24Hr())
                .vwap24Hr(cryptoCurrency.getVwap24Hr())
                .explorer(cryptoCurrency.getExplorer())
                .build();
    }
}
