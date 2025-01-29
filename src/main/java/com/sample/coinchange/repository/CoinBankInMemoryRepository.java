package com.sample.coinchange.repository;

import com.sample.coinchange.dto.CoinType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class CoinBankInMemoryRepository {
    Map<CoinType, Integer> coinCollection;

    @PostConstruct
    public void init() {
        coinCollection = new ConcurrentHashMap<>();
        //Tartget amount $5
        /*coinCollection.put(CoinType.QUARTER, 16);
        coinCollection.put(CoinType.DIME, 5);
        coinCollection.put(CoinType.NICKEL, 8);
        coinCollection.put(CoinType.PENNY, 10);*/
        coinCollection.put(CoinType.QUARTER, 16);
        coinCollection.put(CoinType.DIME, 5);
        coinCollection.put(CoinType.NICKEL, 8);
        coinCollection.put(CoinType.PENNY, 10);
    }

    public Map<CoinType, Integer> getCoinCollection() {
        return coinCollection;
    }
}
