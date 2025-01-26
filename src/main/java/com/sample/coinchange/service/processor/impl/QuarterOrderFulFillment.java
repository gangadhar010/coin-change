package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class QuarterOrderFulFillment extends AbstractOrderFulFillment{

    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        printCoinBankStatus();
        while ((container.getBill() > 0) && (coinBank.getCoinCollection().get(CoinType.QUARTER) > 0)) {
            container.setBill(container.getBill() - CoinType.QUARTER.getAmount().doubleValue());
            coinBank.getCoinCollection().computeIfPresent(CoinType.QUARTER, (k, v) -> v - 1);

            if (container.getResults().containsKey(CoinType.QUARTER)) {
                container.getResults().computeIfPresent(CoinType.QUARTER, ((k, v) -> v + 1));
            } else {
                container.getResults().computeIfAbsent(CoinType.QUARTER, v -> 1);
            }
        }
    }
}
