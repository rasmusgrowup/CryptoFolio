package com.app.CryptoWatcher.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoCurrencyDTO {
    private String id;
    private String rank;
    private String symbol;
    private String name;
    private String supply;
    private String maxSupply;
    private String marketCapUsd;
    private String volumeUsd24Hr;
    private String priceUsd;
    @JsonProperty("changePercent24Hr") // Rename the field to match Java naming convention
    private String changePercent24Hr;
    private String vwap24Hr;
    private String explorer;
}
