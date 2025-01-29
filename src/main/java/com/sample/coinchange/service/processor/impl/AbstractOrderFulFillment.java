package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dao.CoinBankDao;
import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import com.sample.coinchange.modal.CoinEntity;
import com.sample.coinchange.repository.CoinBankInMemoryRepository;
import com.sample.coinchange.service.processor.OrderFulFillment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AbstractOrderFulFillment implements OrderFulFillment {

    @Autowired
    protected CoinBankDao coinBankDao;

    private Boolean oderFulFilled = Boolean.FALSE;

    public Boolean getOderFulFilled() {
        return oderFulFilled;
    }

    public void setOderFulFilled(Boolean oderFulFilled) {
        this.oderFulFilled = oderFulFilled;
    }

    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        printCoinBankStatus();
        CoinEntity coin = coinBankDao.getCoinsByType(getCoinType());
        while ((container.getBill() > 0) && (coin.getAvailableCoins() > 0)) {
            container.setBill(container.getBill() - getCoinType().getAmount().doubleValue());
            coin.setAvailableCoins(coin.getAvailableCoins() -1);

            if (container.getResults().containsKey(getCoinType())) {
                container.getResults().computeIfPresent(getCoinType(), ((k, v) -> v + 1));
            } else {
                container.getResults().computeIfAbsent(getCoinType(), v -> 1);
            }
        }
        coinBankDao.updateCoin(getCoinType(), coin.getAvailableCoins());
    }

    protected void printCoinBankStatus() {
        log.debug("Coin bank current status:");
        coinBankDao.logCurrentDbStatus();
    }

    public CoinType getCoinType() {
        return this.getCoinType();
    }
}
