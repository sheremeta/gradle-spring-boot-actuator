package com.poc.fare;

public enum FareMetrics {

    FARE_REQUESTS_XXX("fare.requests.XXX"),
    FARE_REQUESTS_2XX("fare.requests.2XX"),
    FARE_REQUESTS_4XX("fare.requests.4XX"),
    FARE_REQUESTS_5XX("fare.requests.5XX"),

    FARE_REQUESTS_TIMER("fare.requests.timer");

    private final String code;

    FareMetrics(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
