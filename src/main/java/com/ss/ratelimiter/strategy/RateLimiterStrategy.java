package com.ss.ratelimiter.strategy;

public interface RateLimiterStrategy {
    /**
     * Checks if a request is allowed for the given user ID.
     *
     * @param userId The ID of the user making the request.
     * @return true if the request is allowed, false otherwise.
     */
    boolean isAllowed(String userId);
}
