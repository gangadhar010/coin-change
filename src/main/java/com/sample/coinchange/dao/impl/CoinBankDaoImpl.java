package com.sample.coinchange.dao.impl;

import com.sample.coinchange.constants.DatabaseType;
import com.sample.coinchange.dao.CoinBankDao;
import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.modal.CoinEntity;
import com.sample.coinchange.repository.CoinBankH2Repository;
import com.sample.coinchange.repository.CoinBankInMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CoinBankDaoImpl implements CoinBankDao {

    @Autowired
    private CoinBankH2Repository h2Repository;

    @Autowired
    protected CoinBankInMemoryRepository inMemoryRepository;

    @Value("${active.database}")
    private DatabaseType databaseType;

    @Override
    public void logCurrentDbStatus() {
        switch (databaseType) {
            case H2 -> {
                List<CoinEntity> coins = h2Repository.findAll();
                log.debug("Fetched Coins from DB: {}", coins);
            }
            case IN_MEMORY -> {
                Map<CoinType, Integer> coinData= inMemoryRepository.getCoinCollection();
                log.debug("In memory Coin Data: {}", inMemoryRepository.getCoinCollection());
            }
        }
    }

    @Override
    public void updateCoin(CoinType coinType, Integer updatedCoins) {
        switch (databaseType) {
            case H2 -> {
                CoinEntity coinForUpdate = h2Repository.findByCoinType(coinType.name());
                coinForUpdate.setAvailableCoins(updatedCoins);
                try {
                    h2Repository.save(coinForUpdate);
                } catch (StaleObjectStateException exception) {
                    log.error("Db row updated by another transaction", exception);
                    throw exception;
                }
            }
            case IN_MEMORY -> {
                inMemoryRepository.getCoinCollection()
                        .computeIfPresent(coinType, ((k, v) -> updatedCoins));
            }
        }
    }

    @Override
    public CoinEntity getCoinsByType(CoinType coinType) {
        CoinEntity coin = null;
        switch (databaseType) {
            case H2 -> {
                coin = h2Repository.findByCoinType(coinType.name());
            }
            case IN_MEMORY -> {
                coin = new CoinEntity();
                coin.setCoinType(coinType.name());
                coin.setAvailableCoins(inMemoryRepository.getCoinCollection().get(coinType));
            }
        }
        return coin;
    }
}
