package com.sample.coinchange.controller;

import com.sample.coinchange.dto.AcceptableBills;
import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.CoinChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@Slf4j
public class CoinChangeController {

    @Autowired
    private CoinChangeService coinChangeService;

    @GetMapping(value = "/api/change/{bill}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity calculateChange(@PathVariable Integer bill) {
        log.info("Received request to calculate change for bill {}", bill);
        AcceptableBills accBill = AcceptableBills.findByBill(bill);
        log.info("Acceptable bill status {}", accBill);

        if(null != accBill) {
            Map<CoinType, Integer> result = coinChangeService.getCoinsForGivenBill(bill);
            if (null != result) {
                return  new ResponseEntity(result, HttpStatus.OK);
            } else {
                return  new ResponseEntity("No sufficient coins available in bank." +
                        "Unable to provide exact change!", HttpStatus.NOT_ACCEPTABLE);
            }

        } else {
            return  new ResponseEntity("Non acceptable bill. " +
                    "Unable to process request. \n" +
                    "Acceptable bills are:" + Arrays.stream(AcceptableBills.values()).toList(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
