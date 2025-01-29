package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class NickelOrderFulFillment extends AbstractOrderFulFillment {

    @Override
    public CoinType getCoinType() {
        return CoinType.NICKEL;
    }
}
