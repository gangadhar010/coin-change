package com.sample.coinchange.dao;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.modal.CoinEntity;

public interface CoinBankDao {
    void logCurrentDbStatus();
    void updateCoin(CoinType coinType, Integer updatedCoins);
    CoinEntity getCoinsByType(CoinType coinType);
}
