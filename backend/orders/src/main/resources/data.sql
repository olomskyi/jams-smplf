INSERT INTO orders (order_number, sku_code, price, quantity)
VALUES ('ORD-001', 'iphone_15', 1077.00, 2)
ON DUPLICATE KEY UPDATE
    sku_code = VALUES(sku_code),
    price = VALUES(price),
    quantity = VALUES(quantity);

INSERT INTO orders (order_number, sku_code, price, quantity)
VALUES ('ORD-002', 'galaxy_s24', 999.00, 1)
ON DUPLICATE KEY UPDATE
    sku_code = VALUES(sku_code),
    price = VALUES(price),
    quantity = VALUES(quantity);