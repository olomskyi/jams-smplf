package com.olomsky.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    private static final Logger log = LoggerFactory.getLogger(Routes.class);

    @Bean
    public RouterFunction<ServerResponse> productsRoute() {
        log.info("Routes: productsRoute called");
        return GatewayRouterFunctions.route("products-app")
                .route(RequestPredicates.path("/api/products"),
                        HandlerFunctions.http("http://products-app:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ordersRoute() {
        log.info("Routes: ordersRoute called");
        return GatewayRouterFunctions.route("orders_app")
                .route(RequestPredicates.path("/api/orders"),
                        HandlerFunctions.http("http://orders-app:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {
        log.info("Routes: inventoryRoute called");
        return GatewayRouterFunctions.route("inventory_app")
                .route(RequestPredicates.path("/api/inventory"),
                        HandlerFunctions.http("http://inventory-app:8082"))
                .build();
    }
}
