CREATE USER 'user'@'%' IDENTIFIED BY 'pass';
CREATE DATABASE IF NOT EXISTS jamsdb_orders;
CREATE DATABASE IF NOT EXISTS jamsdb_inventory;
GRANT ALL PRIVILEGES ON jamsdb_orders.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON jamsdb_inventory.* TO 'user'@'%';
FLUSH PRIVILEGES;
