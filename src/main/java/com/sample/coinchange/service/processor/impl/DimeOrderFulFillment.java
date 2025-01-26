package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DimeOrderFulFillment extends AbstractOrderFulFillment{

    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        printCoinBankStatus();
        while ((container.getBill() > 0) && (coinBank.getCoinCollection().get(CoinType.DIME) > 0)) {
            container.setBill(container.getBill() - CoinType.DIME.getAmount().doubleValue());
            coinBank.getCoinCollection().computeIfPresent(CoinType.DIME, (k, v) -> v - 1);

            if (container.getResults().containsKey(CoinType.DIME)) {
                container.getResults().computeIfPresent(CoinType.DIME, ((k, v) -> v + 1));
            } else {
                container.getResults().computeIfAbsent(CoinType.DIME, v -> 1);
            }
        }
    }
}
