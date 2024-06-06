package com.app.CryptoWatcher.repository;

import com.app.CryptoWatcher.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    Optional<CryptoCurrency> findById(String name);
}
