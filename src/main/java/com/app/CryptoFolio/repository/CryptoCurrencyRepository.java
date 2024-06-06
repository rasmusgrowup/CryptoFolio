package com.app.CryptoFolio.repository;

import com.app.CryptoFolio.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    Optional<CryptoCurrency> findById(String name);
}
