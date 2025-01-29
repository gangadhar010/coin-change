package com.sample.coinchange.repository;

import com.sample.coinchange.modal.CoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinBankH2Repository  extends JpaRepository<CoinEntity, Long> {
    CoinEntity findByCoinType(String coinType);
}
