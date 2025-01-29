package com.sample.coinchange.service.processor.impl;

import com.sample.coinchange.dao.CoinBankDao;
import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.OrderFulFillmentContainer;
import com.sample.coinchange.modal.CoinEntity;
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

    @Value("${enterprise.processing.enabled}")
    public boolean enterpriseProcessingEnabled;

    @Autowired
    private CoinBankDao coinBankDao;

    public Map<CoinType, Integer> performOrderFulFillment(Integer bill) {
        log.debug("Before execution coin bank status:");
        coinBankDao.logCurrentDbStatus();
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
            rollbackCoinsToRepository(result);
            return null;
        }
        log.debug("After execution coin bank status:");
        coinBankDao.logCurrentDbStatus();
        return result;
    }

    private Double calcualteCoins(Map<CoinType, Integer> result,
                                Double remainingBalance) {
        log.debug("Remaining Amount: {}", remainingBalance);
        CoinType[] coinTypeArray = CoinType.values();
        Arrays.sort(coinTypeArray, Collections.reverseOrder());

        for (CoinType coinType : coinTypeArray) {
            CoinEntity coin = coinBankDao.getCoinsByType(coinType);
            while ((remainingBalance > 0) && (coin.getAvailableCoins() > 0)) {
                remainingBalance -= coinType.getAmount().doubleValue();
                coin.setAvailableCoins(coin.getAvailableCoins() -1);

                if (result.containsKey(coinType)) {
                    result.computeIfPresent(coinType, ((k, v) -> v + 1));
                } else {
                    result.computeIfAbsent(coinType, v -> 1);
                }
            }
            coinBankDao.updateCoin(coinType, coin.getAvailableCoins());
        }
        log.debug("After All coin spent still remaining amount: {}", remainingBalance);
        return remainingBalance;
    }

    private void rollbackCoinsToRepository(Map<CoinType, Integer> result) {
        log.debug("Rolling back coins to bank.");
        log.debug("Before rollback coin bank status:");
        coinBankDao.logCurrentDbStatus();

        result.entrySet().stream().forEach(entry -> {
            CoinEntity coin = coinBankDao.getCoinsByType(entry.getKey());
            coinBankDao.updateCoin(entry.getKey(), coin.getAvailableCoins() + entry.getValue());
        });
        log.debug("After rollback coin bank status:");
        coinBankDao.logCurrentDbStatus();
    }

}
