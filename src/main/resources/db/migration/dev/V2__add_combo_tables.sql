CREATE TABLE combos (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT,
    title       VARCHAR(300),
    content     TEXT NULL,
    combo_type  VARCHAR(20),
    source_type VARCHAR(20),
    tweet_url   TEXT NULL,
    image_url   TEXT NULL,
    like_count  INT DEFAULT 0,
    view_count  INT NOT NULL DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_combos_user   FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE combo_items (
    id          BIGSERIAL PRIMARY KEY,
    combo_id    BIGINT,
    product_id  BIGINT,
    role        VARCHAR(50) NULL,
    display_order INT,

    CONSTRAINT fk_combo_items_combo FOREIGN KEY (combo_id) REFERENCES combos(id),
    CONSTRAINT fk_combo_items_product    FOREIGN KEY (product_id)    REFERENCES products(id)
);