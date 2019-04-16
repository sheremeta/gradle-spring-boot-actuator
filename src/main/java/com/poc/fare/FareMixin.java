package com.poc.fare;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Currency;

@JsonDeserialize(builder = Fare.Builder.class)
public abstract class FareMixin {

    @JsonCreator
    public FareMixin(
        @JsonProperty("amount") double amount,
        @JsonProperty("currency") Currency currency) {}

}
