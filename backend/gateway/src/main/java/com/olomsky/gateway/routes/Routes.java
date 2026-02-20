package com.olomsky.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> productsRoute() {
        return GatewayRouterFunctions.route("product_service")
                .route(RequestPredicates.path("/api/product"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ordersRoute() {
        return GatewayRouterFunctions.route("orders_service")
                .route(RequestPredicates.path("/api/orders"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryRoute() {
        return GatewayRouterFunctions.route("inventory_service")
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }
}
