-- psql -h localhost -p 5433 -U user -d store_db

-- Удаление всех таблиц, если они существуют, для предотвращения ошибок
drop TABLE IF EXISTS order_products CASCADE;
drop TABLE IF EXISTS prices CASCADE;
drop TABLE IF EXISTS category_product CASCADE;
drop TABLE IF EXISTS images CASCADE;
drop TABLE IF EXISTS orders CASCADE;
drop TABLE IF EXISTS products CASCADE;
drop TABLE IF EXISTS categories CASCADE;
drop TABLE IF EXISTS users CASCADE;

drop sequence IF EXISTS hibernate_sequence CASCADE;

-- Создание таблицы categories
create table categories (
    category_id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    super_category_id BIGINT,
    CONSTRAINT pk_categories PRIMARY KEY (category_id),
    CONSTRAINT FK_CATEGORIES_ON_SUPER_CATEGORY FOREIGN KEY (super_category_id) REFERENCES categories (category_id) ON delete SET NULL
);

-- Создание таблицы users
create table users (
    user_id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_first_name VARCHAR(255) NOT NULL,
    user_last_name VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    role SMALLINT,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

-- Создание таблицы products
create table products (
    product_id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_description VARCHAR(255) NOT NULL,
    product_sku VARCHAR(255) NOT NULL,
    product_quantity BIGINT NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (product_id)
);

-- Создание таблицы images
create table images (
    id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    product_product_id BIGINT,
    image_title VARCHAR(255) NOT NULL,
    image_reference VARCHAR(255) NOT NULL,
    CONSTRAINT pk_images PRIMARY KEY (id),
    CONSTRAINT FK_IMAGES_ON_PRODUCT_PRODUCT FOREIGN KEY (product_product_id) REFERENCES products (product_id) ON delete SET NULL
);

-- Создание таблицы orders
create table orders (
    order_id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_user_id BIGINT,
    order_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (order_id),
    CONSTRAINT FK_ORDERS_ON_USER_USER FOREIGN KEY (user_user_id) REFERENCES users (user_id) ON delete SET NULL
);

-- Создание таблицы prices
create table prices (
    price_id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    product_product_id BIGINT,
    price_value DECIMAL NOT NULL,
    price_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_prices PRIMARY KEY (price_id),
    CONSTRAINT FK_PRICES_ON_PRODUCT_PRODUCT FOREIGN KEY (product_product_id) REFERENCES products (product_id) ON delete CASCADE
);

-- Создание таблицы category_product
create table category_product (
    category_id bigint not null,
    product_id bigint not null,
    constraint pk_category_product primary key (category_id, product_id),
    constraint fk_catpro_on_category foreign key (category_id) references categories (category_id) on delete cascade,
    constraint fk_catpro_on_product foreign key (product_id) references products (product_id) on delete cascade
);

-- Создание таблицы order_products
create table order_products (
    order_id bigint not null,
    product_id bigint not null,
    constraint fk_ordpro_on_order foreign key (order_id) references orders (order_id) on delete cascade,
    constraint fk_ordpro_on_product foreign key (product_id) references products (product_id) on delete cascade
);

create sequence hibernate_sequence start 1;