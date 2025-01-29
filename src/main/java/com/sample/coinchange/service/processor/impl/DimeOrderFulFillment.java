package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DimeOrderFulFillment extends AbstractOrderFulFillment{

    @Override
    public CoinType getCoinType() {
        return CoinType.DIME;
    }
}
