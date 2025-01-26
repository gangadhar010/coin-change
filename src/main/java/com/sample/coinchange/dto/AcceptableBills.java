package com.sample.coinchange.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AcceptableBills {
    ONE(new BigDecimal("1")),
    TWO(new BigDecimal("2")),
    FIVE(new BigDecimal("5")),
    TEN(new BigDecimal("10")),
    TWENTY(new BigDecimal("20")),
    FIFTY(new BigDecimal("50")),
    HUNDRED(new BigDecimal("100"));

    @JsonValue
    private final BigDecimal bill;

    public static AcceptableBills findByBill(Integer bill) {
        return Arrays.stream(values())
                .filter(filter ->  (filter.getBill().intValue() == bill))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return bill.toString();
    }
}
