package com.poc.fare;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Currency;

public class Fare {

    private final double amount;
    private final Currency currency;

    private Fare(Builder builder) {
        this.amount = builder.amount;
        this.currency = builder.currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private double amount;
        private Currency currency;
        private Builder() {}

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Fare build() {
            return new Fare(this);
        }
    }
}
