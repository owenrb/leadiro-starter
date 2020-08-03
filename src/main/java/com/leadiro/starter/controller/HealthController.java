package com.leadiro.starter.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

@RestController
public class HealthController {
    @Value("${app.version}") private String version;
    @Value("${app.timestamp}") private String timestamp;

    /**
     * Return time and version details for load balancer health checks
     */
    @GetMapping(value={ "/ping", "/health", "/starter/health" })
    public Map<String, String> healthy() {
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId.of("UTC"));
        return ImmutableMap.of("time", formatter.format(now), "version", version, "build", timestamp);
    }

}
