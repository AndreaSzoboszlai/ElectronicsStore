DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS carts CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS orders_products CASCADE;
DROP TABLE IF EXISTS carts_products CASCADE;
DROP TABLE IF EXISTS coupons CASCADE;

CREATE TABLE users(
	user_id SERIAL UNIQUE PRIMARY KEY,
	user_name varchar(16) NOT NULL,
	user_email varchar(254) NOT NULL,
	user_password text NOT NULL,
	user_role varchar(10) NOT NULL,
	user_number_purchases integer default 0,
	CONSTRAINT user_name_not_empty CHECK (user_name <> ''),
	CONSTRAINT user_email_not_empty CHECK (user_email <> ''),
	CONSTRAINT user_password_not_empty CHECK (user_password <> '')
);

CREATE TABLE products(
	product_id SERIAL PRIMARY KEY,
	product_name varchar(100),
	product_price integer,
	product_description TEXT,
	product_number_stock integer,
	CONSTRAINT product_name_not_empty CHECK (product_name <> ''),
	CONSTRAINT product_price_min CHECK (product_price > 0),
	CONSTRAINT product_number_stock_min CHECK (product_price >= 0)
);

CREATE TABLE orders(
	order_id SERIAL PRIMARY KEY,
	order_status boolean DEFAULT false,
	ordered_total_price integer,
	user_id integer,
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE carts(
	cart_id SERIAL PRIMARY KEY,
	total_price integer DEFAULT 0,
	cart_discount integer DEFAULT 0,
	user_id integer,
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT cart_discount_max CHECK (cart_discount <= 100)
);

CREATE TABLE carts_products(
	cart_id integer,
	quantity_ordered integer,
	product_per_total integer,
	product_id integer,
	FOREIGN KEY (product_id) REFERENCES products(product_id),
	FOREIGN KEY (cart_id) REFERENCES carts(cart_id)
);

CREATE TABLE orders_products(
	order_id integer,
	quantity_ordered integer,
	product_id integer,
	FOREIGN KEY (order_id) REFERENCES orders(order_id),
	FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE coupons(
	coupon_id SERIAL PRIMARY KEY,
	coupon_name varchar(40),
	coupon_percent integer,
	CONSTRAINT cart_coupon_max CHECK (coupon_percent <= 100 AND coupon_percent >= 0)
);


create or replace function update_order_number() RETURNS trigger AS '
    BEGIN
        IF (TG_OP = ''INSERT'') THEN
            DECLARE
                id integer;
            BEGIN
				SELECT user_id into id FROM orders WHERE user_id = NEW.user_id;
				UPDATE users SET user_number_purchases = (user_number_purchases + 1) WHERE users.user_id = id;
			END;
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

create trigger order_number_check
    after insert on orders
    for each row EXECUTE procedure update_order_number();

create or replace function check_order_stock() RETURNS trigger AS '
    BEGIN
        IF (TG_OP = ''INSERT'') THEN
            DECLARE
				stock_product integer;
                stock_ordered integer;
            BEGIN
				SELECT product_number_stock INTO stock_product FROM products WHERE product_id = NEW.product_id;
                IF stock_product < NEW.quantity_ordered THEN
                	RAISE EXCEPTION ''Not enough product in stock'';
                END IF;
			END;
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

create trigger order_stock_check
    before insert on orders_products
    for each row EXECUTE procedure check_order_stock();

create or replace function increase_total_price() RETURNS trigger AS '
    BEGIN
        IF (TG_OP = ''DELETE'') THEN
			UPDATE carts SET total_price = total_price - OLD.product_per_total WHERE carts.cart_id = OLD.cart_id;
        ELSIF (TG_OP = ''UPDATE'') THEN
			UPDATE carts SET total_price = total_price + (NEW.product_per_total - OLD.product_per_total) WHERE carts.cart_id = OLD.cart_id;
        ELSIF (TG_OP = ''INSERT'') THEN
			UPDATE carts SET total_price = total_price + NEW.product_per_total WHERE carts.cart_id = NEW.cart_id;
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

CREATE TRIGGER update_total_price
    AFTER INSERT OR UPDATE OR DELETE ON carts_products
    FOR EACH ROW EXECUTE procedure increase_total_price();

-- USERS table
INSERT INTO users(user_name, user_email, user_password, user_role) VALUES
	('a', 'a', '1000:52a2e5376fe9155814775f1e3231a526:191ade9da2dcbabfc870ba70263b7af6865b40d8e179d19e8ea504d257810c6e78a316d77f5bd8716a7fa54f39b1f082c773ca80b45526dd59c933522e341216', 'EMPLOYEE'),
	('r', 'r', '1000:12b64240b3c5da1f64daa0d26dbd7bfb:e314534adbb83fa0d605557a1d7394f6936b10efcfc89cae85260e69ad452241cbdd6d043ae51ecc92e8776b4aa369fa6afb028cac5254f9cc7a4e8eae0722c2', 'CUSTOMER'),
	('c', 'c', '1000:12b64240b3c5da1f64daa0d26dbd7bfb:e314534adbb83fa0d605557a1d7394f6936b10efcfc89cae85260e69ad452241cbdd6d043ae51ecc92e8776b4aa369fa6afb028cac5254f9cc7a4e8eae0722c2', 'CUSTOMER');

-- PRODUCTS table
INSERT INTO products(product_name, product_price, product_description, product_number_stock) VALUES
	('HP - ENVY x360 2-in-1', 220, 'Improve productivity with this laptop.', 10),  				--1
	('HP - Chromebook', 300, 'ChromeBook', 6),  												--2
	('Dell - Inspiron 2-in-1 ', 1000, '4K Ultra HD.', 10),  									--3
	('Lenovo - 130-15AST', 400, 'Ideal for student.', 4),  										--4
	('HP - Pavilion x360 2-in-1', 600, 'HP Pavilion x360 Convertible 2-in-1 Laptop.', 2),  		--5
	('Microsoft - Surface Book 2', 1300, 'HP Pavilion x360 Convertible 2-in-1 Laptop.', 1);  	--6

-- ORDERS table
INSERT INTO orders(ordered_total_price, user_id) VALUES
	(2140, 2),		--1
	(1520, 2);		--2


-- ORDER_PRODUCTS table
INSERT INTO orders_products(order_id, quantity_ordered, product_id) VALUES
	(1, 1, 1),		--1
	(1, 1, 2),		--2
	(1, 1, 3),		--3
	(1, 1, 4),		--4
	(1, 1, 1),		--5
	(2, 1, 1),		--6
	(2, 1, 2),		--7
	(2, 1, 3);		--8

-- CARTS table
INSERT INTO carts( user_id) VALUES
	(2),		--1
	(3);		--2

-- CARTS_PRODUCTS table
INSERT INTO carts_products(cart_id, quantity_ordered, product_per_total, product_id) VALUES
	(1, 1, 220, 1),
	(2, 1, 300, 2);

-- COUPONS table
INSERT INTO coupons(coupon_name, coupon_percent) VALUES
	('Summer Sale', 20),		--1
	('June Sale', 15);			--2
UPDATE carts_products SET quantity_ordered = 2, product_per_total = 440 WHERE cart_id = 2 AND product_id = 2;
DELETE FROM carts_products WHERE cart_id = 1 AND product_id = 1;
