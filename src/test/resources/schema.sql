CREATE TABLE product_category (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_product_category PRIMARY KEY (id)
);

CREATE TABLE product (
    id BIGINT AUTO_INCREMENT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    product_category_id BIGINT,

   CONSTRAINT pk_product PRIMARY KEY (id),
   CONSTRAINT fk_product_category FOREIGN KEY (product_category_id)
        REFERENCES product_category(id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT NOT NULL,
    paid BOOLEAN DEFAULT false,
    payment_method SMALLINT DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT NOT NULL,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(38,2) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES cart(id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(id)
);
