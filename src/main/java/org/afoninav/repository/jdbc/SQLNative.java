package org.afoninav.repository.jdbc;

public enum SQLNative {
    PRODUCT_CREATE("INSERT INTO products (product_id, name, price) VALUES (?,?,?);"),
    PRODUCT_GET_COUNT("SELECT count(*) AS count FROM products WHERE name=(?);"),
    PRODUCT_GET_NEXT_ID("SELECT (COALESCE (max(product_id),0)+1) AS id FROM products;"),
    PRODUCT_READ_BY_ID("SELECT * FROM products WHERE product_id=(?);"),
    PRODUCT_READ_ALL("SELECT * FROM products;"),
    PRODUCT_UPDATE("UPDATE products SET name=(?), price=(?) WHERE product_id=(?);"),
    PRODUCT_DELETE_BY_ID("DELETE FROM products WHERE product_id=(?);"),

    CUSTOMER_CREATE("INSERT INTO customers (customer_id,name,phone,email,address) VALUES (?,?,?,?,?);"),
    CUSTOMER_GET_COUNT("SELECT count(*) AS count FROM customers WHERE phone=(?);"),
    CUSTOMER_GET_NEXT_ID("SELECT (COALESCE (max(customer_id), 0)+1) AS id  FROM customers;"),
    CUSTOMER_READ_BY_ID("SELECT * FROM customers WHERE customer_id=(?);"),
    CUSTOMER_READ_ALL("SELECT * FROM customers;"),
    CUSTOMER_UPDATE("UPDATE customers SET name=(?),phone=(?),email=(?),address=(?) WHERE customer_id=(?);"),
    CUSTOMER_CHANGE_STATUS("UPDATE customers SET status=FALSE WHERE customer_id=(?);"),
    CUSTOMER_GET_ALL_ORDERS_BY_CUSTOMER_ID("SELECT \tord.order_id,\n" +
            "\t\tord.comment,\n" +
            "\t\tord.status\n" +
            "FROM customers cu LEFT JOIN orders ord ON cu.customer_id = ord.customer_id\n" +
            "WHERE cu.customer_id = (?);"),

    ORDER_CREATE("INSERT INTO orders (order_id, customer_id, comment) VALUES (?,?,?);"),
    ORDER_GET_NEXT_ID("SELECT (COALESCE(max(order_id),0)+1) AS id FROM orders;"),
    ORDER_READ_BY_ID("SELECT * FROM orders WHERE order_id=(?);"),
    ORDER_UPDATE("UPDATE orders SET comment=(?), status=(?), customer_id=(?) WHERE order_id=(?);"),
    ORDER_CHANGE_STATUS("UPDATE orders SET status=FALSE WHERE order_id=(?);"),
    ORDER_READ_ALL("SELECT * FROM orders;"),
    ORDER_GET_PRODUCTS_BY_ORDER_ID("SELECT \tpr.product_id,\n" +
            "\t\tpr.name,\n" +
            "\t\tpr.price\n" +
            "FROM orders ord LEFT JOIN orders_products op ON ord.order_id = op.order_id \n" +
            "\t\t\t\tLEFT JOIN products pr ON op.product_id = pr.product_id \n" +
            "WHERE ord.order_id = (?); "),
    ORDER_ERASE_ORDER_DETAILS_BY_ORDER_ID("DELETE FROM orders_products WHERE order_id = (?);"),
    ORDER_CREATE_NEW_ORDER_DETAILS_BY_ORDER_ID("INSERT INTO orders_products (order_id, product_id) VALUES (?,?);"),


    ;




    final String QUERY;

    SQLNative(String query) {
        this.QUERY = query;
    }
}
