package com.olomsky.orders.client;

/*import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;*/
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.service.annotation.HttpExchange;

//@FeignClient(value = "inventory", url = "http://jams-inventory:8082")
@HttpExchange
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    //@RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
