package com.poc.fare;

import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.Random;

@Service
public class TransferFareService implements FareService {


    @Override
    public Fare get(String origin, String destination) {
        return Fare.builder()
                .currency(Currency.getInstance("EUR"))
                .amount(new Random().nextDouble())
                .build();
    }
}
