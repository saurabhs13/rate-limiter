package com.ss.ratelimiter.strategy;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ss.ratelimiter.model.TokenBucket;

import lombok.Getter;
import lombok.Setter;


@Component("tokenBucketRateLimiterStrategy")
public class TokenBucketRateLimiterStrategy implements RateLimiterStrategy {
    
    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Value("${rate-limiter.token-bucket.capacity}")
    private int capacity;

    @Value("${rate-limiter.token-bucket.refill-rate-per-second}")
    private double refillRatePerSecond;

    @Override
    public boolean isAllowed(String userId) {
        // Create a new token bucket for the user if it doesn't exist
        buckets.putIfAbsent(userId, new TokenBucket(capacity, refillRatePerSecond));
        // Get the user's token bucket
        TokenBucket userTokenBucket = buckets.get(userId);
        // Check if the request is allowed
        return userTokenBucket.allowRequest();  
    }
}