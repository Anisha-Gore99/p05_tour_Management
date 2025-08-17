package com.example.demo;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutes {

  @Bean
  RouteLocator routes(RouteLocatorBuilder rlb) {
    return rlb.routes()

      // ---------- USER SERVICE ----------
      .route("user-service", r -> r
        .path(
          "/api/user/**", "/auth/**",
          "/api/tourist/**", "/tourist/**",
          "/api/touragency/**", "/touragency/**",
          "/api/roles/**", "/roles/**"
        )
        .filters(f -> f
          .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
          .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE"))
        .uri("lb://USER-SERVICE"))   // ← match Eureka name exactly

      // ---------- TOUR SERVICE ----------
      .route("tour-service", r -> r
        .path("/api/packages/**", "/api/schedules/**", "/api/categories/**")
        .filters(f -> f
          .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
          .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE"))
        .uri("lb://TOUR-SERVICE"))   // ← match Eureka name exactly

      // ---------- BOOKING SERVICE ----------
      .route("booking-service", r -> r
        .path("/api/bookings/**", "/api/cancellations/**", "/api/modes/**")
        .filters(f -> f
          .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
          .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE"))
        .uri("lb://BOOKING-SERVICE"))

      // ---------- ADMIN SERVICE ----------
      .route("admin-service", r -> r
        .path("/api/admin/**")
        .filters(f -> f
          .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
          .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE"))
        .uri("lb://ADMIN-SERVICE"))

      .build();
  }
}
