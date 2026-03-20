
CREATE TABLE product_tags (
                              id          BIGSERIAL       PRIMARY KEY,
                              product_id  BIGINT          NOT NULL,
                              tag_id      BIGINT          NOT NULL,

                              CONSTRAINT fk_product_tags_product
                                  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                              CONSTRAINT fk_product_tags_tag
                                  FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);