package com.ss.ratelimiter.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ss.ratelimiter.service.RateLimiterService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

      

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;
    /**
     * This method is called before the request is handled.
     * It checks if the request is allowed based on the rate limiting strategy.
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @param handler  The handler to be executed
     * @return true if the request is allowed, false otherwise
     * @throws Exception if an error occurs
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-User-Id");
        if (userId == null || !rateLimiterService.isAllowed(userId)) {
            response.setStatus(429); // Too Many Requests
            return false;
        }
        return true;
    }
}
