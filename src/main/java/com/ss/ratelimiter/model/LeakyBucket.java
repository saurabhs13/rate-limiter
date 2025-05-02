package com.ss.ratelimiter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Leaky Bucket Rate Limiter
 * This class implements a leaky bucket rate limiter.
 * It allows a fixed number of requests to be processed per second.
 * If the bucket is full, requests are dropped.
 */
public class LeakyBucket {
    
    @Getter @Setter
    private final int capacity;
    @Getter @Setter 
    private double tokens;
    @Getter @Setter
    private final double leakRatePerSecond;
    @Getter @Setter
    private long lastLeakTimestamp;

    public LeakyBucket(int capacity, double leakRatePerSecond) {
        this.capacity = capacity;
        this.tokens = 0;
        this.leakRatePerSecond = leakRatePerSecond;
        this.lastLeakTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        leak();
        if (tokens < capacity) {
            tokens += 1;
            return true;
        }
        return false;
    }
    private void leak() {
        long now = System.nanoTime();
        double secondsSinceLastLeak = (now - lastLeakTimestamp) / 1_000_000_000.0;
        double tokensToRemove = secondsSinceLastLeak * leakRatePerSecond;
        tokens = Math.max(0, tokens - tokensToRemove);
        lastLeakTimestamp = now;
    }
}