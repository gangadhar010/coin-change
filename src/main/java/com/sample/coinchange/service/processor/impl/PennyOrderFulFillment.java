package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class PennyOrderFulFillment extends AbstractOrderFulFillment{
    @Override
    public void fulFillOrder(OrderFulFillmentContainer container) {
        printCoinBankStatus();
        while ((container.getBill() > 0) && (coinBank.getCoinCollection().get(CoinType.PENNY) > 0)) {
            container.setBill(container.getBill() - CoinType.PENNY.getAmount().doubleValue());
            coinBank.getCoinCollection().computeIfPresent(CoinType.PENNY, (k, v) -> v - 1);

            if (container.getResults().containsKey(CoinType.PENNY)) {
                container.getResults().computeIfPresent(CoinType.PENNY, ((k, v) -> v + 1));
            } else {
                container.getResults().computeIfAbsent(CoinType.PENNY, v -> 1);
            }
        }
    }
}
