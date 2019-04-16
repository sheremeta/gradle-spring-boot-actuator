package com.poc.fare;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import static com.poc.fare.FareMetrics.FARE_REQUESTS_2XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_4XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_5XX;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_TIMER;
import static com.poc.fare.FareMetrics.FARE_REQUESTS_XXX;
import static com.google.common.base.Stopwatch.createStarted;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class FareDiagnosticFilter implements Filter {

    private final Counter requestsXXXCount;
    private final Counter requests2XXCount;
    private final Counter requests4XXCount;
    private final Counter requests5XXCount;

    private final Timer timer;

    @Autowired
    public FareDiagnosticFilter(MeterRegistry registry) {

        requestsXXXCount = registry.counter(FARE_REQUESTS_XXX.getCode());
        requests2XXCount = registry.counter(FARE_REQUESTS_2XX.getCode());
        requests4XXCount = registry.counter(FARE_REQUESTS_4XX.getCode());
        requests5XXCount = registry.counter(FARE_REQUESTS_5XX.getCode());

        timer = registry.timer(FARE_REQUESTS_TIMER.getCode());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        requestsXXXCount.increment();

        Stopwatch stopwatch = createStarted();

        chain.doFilter(request, response);

        timer.record(stopwatch.elapsed(MILLISECONDS), MILLISECONDS);

        HttpStatus httpStatus = HttpStatus.valueOf(((HttpServletResponse) response).getStatus());

        if (httpStatus.is2xxSuccessful()) {
            requests2XXCount.increment();
        } else if (httpStatus.is4xxClientError()) {
            requests4XXCount.increment();
        } else if (httpStatus.is5xxServerError()) {
            requests5XXCount.increment();
        }
    }
}
