package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class PennyOrderFulFillment extends AbstractOrderFulFillment{

    @Override
    public CoinType getCoinType() {
        return CoinType.PENNY;
    }
}
