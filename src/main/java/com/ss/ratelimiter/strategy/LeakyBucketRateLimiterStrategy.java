package com.ss.ratelimiter.strategy;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import com.ss.ratelimiter.model.LeakyBucket;

import lombok.Getter;
import lombok.Setter;

/**
 * Leaky Bucket Rate Limiter Strategy
 * This class implements a leaky bucket rate limiter strategy.
 * It allows a fixed number of requests to be processed per second.
 * If the bucket is full, requests are dropped.
 */
@Component("leakyBucketRateLimiterStrategy")
public class LeakyBucketRateLimiterStrategy implements RateLimiterStrategy {

    private final ConcurrentHashMap<String, LeakyBucket> buckets = new ConcurrentHashMap<>();

    @Value("${rate-limiter.leaky-bucket.capacity}")
    int capacity;
    @Value("${rate-limiter.leaky-bucket.leak-rate-per-second}")
    double leakRatePerSecond;

    @Override
    public boolean isAllowed(String userId) {
        buckets.putIfAbsent(userId, new LeakyBucket(capacity, leakRatePerSecond));
        // Get the user's leaky bucket
        LeakyBucket userLeakyBucket = buckets.get(userId);
        // Check if the request is allowed
    
        return userLeakyBucket.allowRequest();
    }
   
}