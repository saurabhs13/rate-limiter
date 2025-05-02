package com.ss.ratelimiter.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok("Request successful for user: " + userId);
    }
}
