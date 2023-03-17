SET FOREIGN_KEY_CHECKS = 0;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS customers (
	customer_id bigint AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) NOT NULL,
	phone varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	address varchar(255) NOT NULL,
	status boolean DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products (
	product_id bigint AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) NOT NULL,
	price double NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    order_id bigint AUTO_INCREMENT PRIMARY KEY,
    comment varchar(255) NOT NULL,
    status boolean DEFAULT TRUE,
    customer_id bigint NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS orders_products (
	order_id bigint,
	product_id bigint,
	FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders_products;

INSERT INTO customers
(customer_id, name,phone,email,address)
VALUES
(1, 'Leonid','+74952356575','leonid@mail.ru','Belorussia, Minsk'),
(2, 'Avgust','+74961278999','avg@gmail.com','Russia, Petropavlovsk'),
(3, 'Isijdor','+73211232323','isi@gmail.com','Holland, Copengagen');

INSERT INTO products (product_id, name, price)
VALUES 	(1, 'Pizza',600),
		(2, 'Sushi',500),
		(3, 'Wok',250),
		(4, 'Cake',150),
		(5, 'Juse',100),
		(6, 'Tea', 50);

INSERT INTO orders (order_id, customer_id, comment)
VALUES 	(1,1, 'Хорошо упаковать'),
		(2,1,'Жёсткая упаковка'),
		(3,2,'Подписать коробки, что где лежит');

INSERT INTO orders_products(order_id, product_id)
VALUES 	(1,1),(1,1),(1,6),(1,4),
		(2,2),(2,3),(2,6),(2,6),
		(3,3),(3,3),(3,6),(3,6);

SELECT * FROM customers;
SELECT * FROM products;
SELECT * FROM orders;
SELECT * FROM orders_products;

/**** PRODUCTS ***************************************************************************/
INSERT INTO products (product_id, name, price) VALUES (?,?,?);
SELECT count(*) AS count FROM products WHERE name = (?);
SELECT (COALESCE (max(product_id),0)+1) AS id FROM products;
SELECT * FROM products WHERE product_id = (?);
SELECT * FROM products;
UPDATE products SET name = (?), price = (?) WHERE product_id = (?);
DELETE FROM products WHERE product_id = (?);

/**** CUSTOMERS **************************************************************************/
INSERT INTO customers (customer_id,name,phone,email,address) VALUES (?,?,?,?,?);
SELECT count(*) AS count FROM customers WHERE phone = (?);
SELECT (COALESCE (max(customer_id), 0)+1) AS id  FROM customers;
SELECT * FROM customers WHERE customer_id = (?);
SELECT * FROM customers;
UPDATE customers SET name=(?), phone=(?), email=(?), address=(?) WHERE customer_id = (?);
UPDATE customers SET status = false WHERE customer_id=(?);
/****/
SELECT 	ord.order_id,
		ord.comment,
		ord.status
FROM customers cu LEFT JOIN orders ord ON cu.customer_id = ord.customer_id;
WHERE cu.customer_id = (?);
/****/
/**** ORDERS *****************************************************************************/
INSERT INTO orders (order_id, customer_id, comment) VALUES (?,?,?);
SELECT (COALESCE(max(order_id),0)+1) AS id FROM orders;
SELECT * FROM orders WHERE order_id = (?);
UPDATE orders SET comment=(?), status=(?), customer_id=(?) WHERE order_id=(?);
UPDATE orders SET status=FALSE WHERE order_id=(?);
SELECT * FROM orders;

/**** ORDERS_PRODUCT ********************************************************************/

/*** read all products by order id ****/
SELECT 	pr.product_id,
		pr.name,
		pr.price
FROM orders ord LEFT JOIN orders_products op ON ord.order_id = op.order_id
				LEFT JOIN products pr ON op.product_id = pr.product_id
WHERE ord.order_id = (?);

/*** persist all products by order id ****/

 -- 1 erase all products from orders_products;
DELETE FROM orders_products WHERE order_id = (?);

 -- 2 save all new products to orders_products;
INSERT INTO orders_products (order_id, product_id) VALUES (?,?);
/*****************************************************************************************/

