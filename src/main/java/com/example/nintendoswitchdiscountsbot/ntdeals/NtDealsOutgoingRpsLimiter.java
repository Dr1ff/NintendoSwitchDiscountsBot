package com.example.nintendoswitchdiscountsbot.ntdeals;

import javax.annotation.PostConstruct;

import com.revinate.guava.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Alexander Popov
 */
@Component
public class NtDealsOutgoingRpsLimiter {

    @Value("${ntdeals.rate-limiter.max-rps:1}")
    private double maxRps;
    private RateLimiter rateLimiter;

    @PostConstruct
    private void setUp() {
        rateLimiter = RateLimiter.create(maxRps);
    }

    public void acquire() {
        rateLimiter.acquire();
    }

    @SneakyThrows
    public void sleep(long millis) {
        Thread.sleep(millis);
    }
}
