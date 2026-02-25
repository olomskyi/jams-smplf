package com.olomsky.orders.config;

import com.olomsky.orders.client.InventoryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
@Slf4j
public class RestClientConfig {

    @Value("${inventory.url}")
    private String inventoryServerUrl;

    @Bean
    public InventoryClient inventoryClient() {
        log.info("Initializing InventoryClient with URL: {}", inventoryServerUrl);

        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServerUrl)
                .requestFactory(getRequestFactory())
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }

    private ClientHttpRequestFactory getRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(7).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(7).toMillis());
        return factory;
    }
}
