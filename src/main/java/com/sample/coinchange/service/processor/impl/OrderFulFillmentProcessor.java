package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import com.sample.coinchange.repository.CoinBankRepository;
import com.sample.coinchange.service.processor.OrderFulFillment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OrderFulFillmentProcessor {

    @Autowired
    private List<OrderFulFillment> ordersFulFillmentList;
    @Autowired
    protected CoinBankRepository coinBank;

    @Value("${enterprise.processing.enabled}")
    public boolean enterpriseProcessingEnabled;

    public Map<CoinType, Integer> performOrderFulFillment(Integer bill) {
        Map<CoinType, Integer> result = new LinkedHashMap<>();
        Double remainingBalance;

        if(enterpriseProcessingEnabled) {
            OrderFulFillmentContainer container = OrderFulFillmentContainer
                    .builder()
                    .bill(Integer.valueOf(bill).doubleValue())
                    .results(new LinkedHashMap<>()).build();

            ordersFulFillmentList.forEach(
                    orderFulFillment -> orderFulFillment.fulFillOrder(container));
            remainingBalance = container.getBill();
            result = container.getResults();
        } else {
            remainingBalance = calcualteCoins(result, Integer.valueOf(bill).doubleValue());
        }

        log.debug("Prepared response in order fulfillment: {}", result);
        if(remainingBalance.intValue() > 0) {
            return null;
        }
        return result;
    }

    private Double calcualteCoins(Map<CoinType, Integer> result,
                                Double remainingBalance) {
        log.debug("Coin bank status: {}", coinBank.getCoinCollection());
        log.debug("Remaining Amount: {}", remainingBalance);
        CoinType[] coinTypeArray = CoinType.values();
        Arrays.sort(coinTypeArray, Collections.reverseOrder());

        for (CoinType coinType : coinTypeArray) {
            while ((remainingBalance > 0) && (coinBank.getCoinCollection().get(coinType) > 0)) {
                remainingBalance -= coinType.getAmount().doubleValue();
                coinBank.getCoinCollection().computeIfPresent(coinType, (k, v) -> v - 1);

                if (result.containsKey(coinType)) {
                    result.computeIfPresent(coinType, ((k, v) -> v + 1));
                } else {
                    result.computeIfAbsent(coinType, v -> 1);
                }
            }
        }
        log.debug("After All coin spent still remaining amount: {}", remainingBalance);
        return remainingBalance;
    }


}
