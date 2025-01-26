package com.sample.coinchange.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class OrderFulFillmentContainer {
    private Double bill;
    private Map<CoinType, Integer> results;
}
