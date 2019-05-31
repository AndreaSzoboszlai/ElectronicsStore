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
	product_type varchar(40),
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

create or replace function increase_product_count() RETURNS trigger AS '
    BEGIN
		DECLARE
			id integer;
		BEGIN
			SELECT product_id INTO id FROM basket WHERE basket_id = NEW.basket.id;
			IF id = NEW.product_id THEN
				UPDATE basket SET quantity_ordered = quantityordered + 1 WHERE basket_id = NEW.basket_id;
			END IF;
		END;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

create trigger check_duplicates_in_cart
    before insert on carts
    for each row EXECUTE procedure increase_product_count();

insert into users(user_name, user_email, user_password, user_role) values ('a', 'a', 'a', 'EMPLOYEE');
insert into users(user_name, user_email, user_password, user_role) values ('r', 'r', 'r', 'CUSTOMER');
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product1', 20000, 'producttype', 'product1 descript', 10);
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product2', 20000, 'producttype', 'product1 descript', 10);
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product3', 20000, 'producttype', 'product1 descript', 10);
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product4', 20000, 'producttype', 'product1 descript', 10);
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product5', 20000, 'producttype', 'product1 descript', 10);INSERT into orders(ordered_total_price, user_id) values (20000, 1);
insert into products(product_name, product_price, product_type, product_description, product_number_stock) values ('product6', 20000, 'producttype', 'product1 descript', 10);
INSERT into orders(ordered_total_price, user_id) values (50000, 1);
INSERT into orders(ordered_total_price, user_id) values (60000, 1);
INSERT into orders(ordered_total_price, user_id) values (60000, 1);
INSERT into orders(ordered_total_price, user_id) values (60000, 2);
INSERT into orders(ordered_total_price, user_id) values (60000, 2);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (1, 1, 1);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (1, 1, 2);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (1, 1, 3);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (1, 1, 4);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (1, 1, 1);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (2, 1, 1);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (2, 1, 2);
INSERT INTO orders_products(order_id, quantity_ordered, product_id) values (2, 1, 3);

