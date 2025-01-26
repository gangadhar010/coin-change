package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class NickelOrderFulFillment extends AbstractOrderFulFillment {

    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        printCoinBankStatus();
        while ((container.getBill() > 0) && (coinBank.getCoinCollection().get(CoinType.NICKEL) > 0)) {
            container.setBill(container.getBill() - CoinType.NICKEL.getAmount().doubleValue());
            coinBank.getCoinCollection().computeIfPresent(CoinType.NICKEL, (k, v) -> v - 1);

            if (container.getResults().containsKey(CoinType.NICKEL)) {
                container.getResults().computeIfPresent(CoinType.NICKEL, ((k, v) -> v + 1));
            } else {
                container.getResults().computeIfAbsent(CoinType.NICKEL, v -> 1);
            }
        }
    }
}
