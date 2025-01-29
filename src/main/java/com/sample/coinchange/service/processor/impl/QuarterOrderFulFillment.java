package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class QuarterOrderFulFillment extends AbstractOrderFulFillment{

    @Override
    public CoinType getCoinType() {
        return CoinType.QUARTER;
    }
}
