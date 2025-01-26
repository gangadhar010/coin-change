package com.sample.coinchange.service.processor;

import com.sample.coinchange.dto.OrderFulFillmentContainer;

public interface OrderFulFillment {

    void fulFillOrder(OrderFulFillmentContainer container);
}
