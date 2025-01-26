package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;

import java.util.Map;

public interface CoinChangeService {

    Map<CoinType, Integer> getCoinsForGivenBill(Integer bill);
}
