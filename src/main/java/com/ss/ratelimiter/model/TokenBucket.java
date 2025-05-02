package com.ss.ratelimiter.model;

import lombok.Getter;
import lombok.Setter;


public class TokenBucket {

    @Getter @Setter
    private final int capacity;
    @Getter @Setter
    private double tokens;
    @Getter @Setter
    private final double refillRatePerSecond;
    @Getter @Setter
    private long lastRefillTimestamp;

    public TokenBucket(int capacity, double refillRatePerSecond) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        if (refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("Refill rate must be greater than 0");
        }
        this.capacity = capacity;
        this.tokens = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refill();
        if (tokens >= 1) {
            tokens -= 1;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.nanoTime();
        double secondsSinceLast = (now - lastRefillTimestamp) / 1_000_000_000.0;
        double tokensToAdd = secondsSinceLast * refillRatePerSecond;
        tokens = Math.min(capacity, tokens + tokensToAdd);
        lastRefillTimestamp = now;
    }
  
}
