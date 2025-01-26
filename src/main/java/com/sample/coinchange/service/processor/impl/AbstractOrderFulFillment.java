package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.OrderFulFillmentContainer;
import com.sample.coinchange.repository.CoinBankRepository;
import com.sample.coinchange.service.processor.OrderFulFillment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AbstractOrderFulFillment implements OrderFulFillment {

    @Autowired
    protected CoinBankRepository coinBank;

    private Boolean oderFulFilled = Boolean.FALSE;

    public Boolean getOderFulFilled() {
        return oderFulFilled;
    }

    public void setOderFulFilled(Boolean oderFulFilled) {
        this.oderFulFilled = oderFulFilled;
    }

    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        this.fulFillOrder(container);
    }

    protected void printCoinBankStatus() {
        log.debug("Coin bank status:" + coinBank.getCoinCollection());
    }
}
