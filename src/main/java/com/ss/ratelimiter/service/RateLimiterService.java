package com.ss.ratelimiter.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ss.ratelimiter.strategy.RateLimiterStrategy;

@Component
public class RateLimiterService {

    @Autowired
    @Qualifier("tokenBucketRateLimiterStrategy")
    RateLimiterStrategy rateLimiterStrategy;

    public boolean isAllowed(String userId) {
        return getRateLimiterStrategy().isAllowed(userId);
    }
    public void setRateLimiterStrategy(RateLimiterStrategy rateLimiterStrategy) {
        this.rateLimiterStrategy = rateLimiterStrategy;
    }
    public RateLimiterStrategy getRateLimiterStrategy() {
        return rateLimiterStrategy;
    }
   
}
