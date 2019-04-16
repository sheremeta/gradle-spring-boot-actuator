package com.poc.fare;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.poc.fare.FareMetrics.FARE_REQUESTS_2XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_4XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_5XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_TIMER;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_XXX;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static io.micrometer.core.instrument.Meter.Type.COUNTER;
import static io.micrometer.core.instrument.Meter.Type.TIMER;

@Configuration
public class FareConfiguration {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> fareRequestsTotal() {
        return registry -> registry.config().namingConvention().name(FARE_REQUESTS_XXX.getCode(), COUNTER);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> fareRequests2XX() {
        return registry -> registry.config().namingConvention().name(FARE_REQUESTS_2XX.getCode(), COUNTER);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> fareRequests4XX() {
        return registry -> registry.config().namingConvention().name(FARE_REQUESTS_4XX.getCode(), COUNTER);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> fareRequests5XX() {
        return registry -> registry.config().namingConvention().name(FARE_REQUESTS_5XX.getCode(), COUNTER);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> fareRequestsTime() {
        return registry -> registry.config().namingConvention().name(FARE_REQUESTS_TIMER.getCode(), TIMER);
    }

    @Bean
    public FilterRegistrationBean<FareDiagnosticFilter> loggingFilter(FareDiagnosticFilter fareDiagnosticFilter) {
        FilterRegistrationBean<FareDiagnosticFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(fareDiagnosticFilter);
        registrationBean.addUrlPatterns("/fares/*");

        return registrationBean;
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
            .failOnUnknownProperties(false)
            .serializationInclusion(NON_NULL)
            .modules(new FareJacksonModule());
    }
}
