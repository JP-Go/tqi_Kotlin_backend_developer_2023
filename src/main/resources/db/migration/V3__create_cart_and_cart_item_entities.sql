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