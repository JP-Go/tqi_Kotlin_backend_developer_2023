CREATE TABLE product (
    id BIGINT AUTO_INCREMENT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    product_category_id BIGINT,

   CONSTRAINT pk_product PRIMARY KEY (id),
   CONSTRAINT fk_product_category FOREIGN KEY (product_category_id)
        REFERENCES product_category(id) ON DELETE SET NULL ON UPDATE CASCADE
)
