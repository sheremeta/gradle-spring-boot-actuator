package com.poc.fare;

import com.poc.fare.infra.ControllerBaseTest;
import org.junit.Test;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

public class FareControllerAcceptanceTest extends ControllerBaseTest {

    @Test
    public void getFare() {
        Fare fare = get(url("/fares/AAL/ADB")).getBody();

        assertThat(fare).isNotNull();
        assertThat(fare.getAmount()).isNotNull();
        assertThat(fare.getCurrency()).isEqualTo(Currency.getInstance("EUR"));
    }
}
