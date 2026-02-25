package com.olomsky.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.StatusAggregator;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class RoutesConfig {

    private static final Logger log = LoggerFactory.getLogger(RoutesConfig.class);

    @Bean
    public RouterFunction<ServerResponse> productsRoute() {
            log.info("Routes: productsRoute called");
            return GatewayRouterFunctions.route("products-app")
                    .route(RequestPredicates.path("/api/products"),
                           HandlerFunctions.http("http://products-app:8080"))
                    .filter(CircuitBreakerFilterFunctions.circuitBreaker("productsAppCircuitBreaker",
                            URI.create("forward:/fallbackRoute")))
                    .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ordersRoute() {
            log.info("Routes: ordersRoute called");
            return GatewayRouterFunctions.route("orders_app")
                    .route(RequestPredicates.path("/api/orders"),
                           HandlerFunctions.http("http://orders-app:8081"))
                    .filter(CircuitBreakerFilterFunctions.circuitBreaker("ordersAppCircuitBreaker",
                            URI.create("forward:/fallbackRoute")))
                    .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {
            log.info("Routes: inventoryRoute called");
            return GatewayRouterFunctions.route("inventory_app")
                    .route(RequestPredicates.path("/api/inventory/**"),
                           HandlerFunctions.http("http://inventory-app:8082"))
                    .filter(CircuitBreakerFilterFunctions.circuitBreaker("inventoryAppCircuitBreaker",
                            URI.create("forward:/fallbackRoute")))
                    .build();
    }

    // Swagger Open API Doc

    @Bean
    public RouterFunction<ServerResponse> productsDocsRoute() {
        return GatewayRouterFunctions.route("products_app_docs")
                .route(RequestPredicates.path("/aggregate/products-app/v3/api-docs"),
                       HandlerFunctions.http("http://products-app:8080"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ordersDocsRoute() {
        return GatewayRouterFunctions.route("orders_app_docs")
                .route(RequestPredicates.path("/aggregate/orders-app/v3/api-docs"),
                       HandlerFunctions.http("http://orders-app:8081"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryDocsRoute() {
        return GatewayRouterFunctions.route("inventory_app_docs")
                .route(RequestPredicates.path("/aggregate/inventory-app/v3/api-docs"),
                       HandlerFunctions.http("http://inventory-app:8082"))
                .filter(setPath("/api-docs"))
                .build();
    }

    // Circuit breaker impl

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service unavailable, please try again later"))
                .build();
    }

    @Bean
    public StatusAggregator statusAggregator() {
        return StatusAggregator.getDefault();
    }
}
