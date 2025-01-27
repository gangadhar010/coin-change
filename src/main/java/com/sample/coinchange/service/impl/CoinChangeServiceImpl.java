package com.sample.coinchange.service.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.CoinChangeService;
import com.sample.coinchange.service.processor.impl.OrderFulFillmentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class CoinChangeServiceImpl implements CoinChangeService {

    @Autowired
    private OrderFulFillmentProcessor orderFulFillmentProcessor;

    @Override
    public Map<CoinType, Integer> getCoinsForGivenBill(Integer bill) {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            Map<CoinType, Integer> result =  orderFulFillmentProcessor.performOrderFulFillment(bill);
            log.debug("Prepared response in Service layer: {}", result);
            return result;
        } finally {
            lock.unlock();
        }
    }

}
