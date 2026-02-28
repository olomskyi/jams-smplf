package com.olomsky.notifications.order;

import java.math.BigDecimal;

public record OrderEventOld(
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity,
        String email,
        String firstName,
        String lastName
) {}