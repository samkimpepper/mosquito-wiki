CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    name        VARCHAR(100) NOT NULL,
    provider    VARCHAR(20),
    provider_id VARCHAR(200),
    profile_image_url TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_provider_user UNIQUE (provider, provider_id)
);

CREATE TABLE brands (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200),
    name_ko     VARCHAR(200),
    slug        VARCHAR(200) UNIQUE,
    logo_url    TEXT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100),
    slug        VARCHAR(100) UNIQUE
);

CREATE TABLE products (
    id          BIGSERIAL PRIMARY KEY,
    parent_id   BIGINT NULL,
    brand_id    BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    name        VARCHAR(200) NULL,
    name_ko     VARCHAR(300) NULL,
    option_name  VARCHAR(200) NULL,
    option_name_ko VARCHAR(300) NULL,
    slug        VARCHAR(300) NOT NULL UNIQUE,
    description TEXT NULL,
    official_image_urls jsonb NULL DEFAULT '[]',
    view_count  INT NOT NULL DEFAULT 0,
    like_count  INT DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by  BIGINT NULL,
    modified_at TIMESTAMP NULL DEFAULT NOW(),
    modified_by BIGINT NULL,

    CONSTRAINT fk_products_parent FOREIGN KEY (parent_id) REFERENCES products(id),
    CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES brands(id),
    CONSTRAINT fk_products_user FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_products_user_modified FOREIGN KEY (modified_by) REFERENCES users(id)
);

CREATE TABLE swatches (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NULL,
    title       TEXT NULL,
    content     TEXT NULL,
    source_type VARCHAR(20), -- upload or twitter
    tweet_url   TEXT NULL,
    image_urls jsonb NULL DEFAULT '[]',
    like_count  INT DEFAULT 0,
    view_count  INT NOT NULL DEFAULT 0,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP NULL DEFAULT NOW(),
    modified_by BIGINT NULL,

    CONSTRAINT fk_swatches_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_swatches_user_modified FOREIGN KEY (modified_by) REFERENCES users(id)
);

CREATE TABLE tags (
                      id          BIGSERIAL       PRIMARY KEY,
                      tag_type    VARCHAR(100)    ,
                      tag_value   VARCHAR(255)    NOT NULL unique ,
                      color       VARCHAR(7)
);

CREATE TABLE swatch_tags (
    id          BIGSERIAL PRIMARY KEY,
    swatch_id   BIGINT NULL,
    tag_id      BIGINT NOT NULL,

    CONSTRAINT fk_swatch_tags_swatch FOREIGN KEY (swatch_id) REFERENCES swatches(id),
    CONSTRAINT fk_swatch_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

CREATE TABLE swatch_links (
    id          BIGSERIAL PRIMARY KEY,
    swatch_id    BIGINT NOT NULL ,
    product_id              BIGINT NOT NULL ,
    display_order           INT,

    CONSTRAINT fk_swatch_links_swatch FOREIGN KEY (swatch_id) REFERENCES swatches(id),
    CONSTRAINT fk_swatch_links_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE product_follows (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT,
    product_id  BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_user_product UNIQUE (user_id, product_id),
    CONSTRAINT fk_product_follows_user    FOREIGN KEY (user_id)    REFERENCES users(id),
    CONSTRAINT fk_product_follows_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE product_likes (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT,
    product_id  BIGINT,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_user_product_likes UNIQUE (user_id, product_id),
    CONSTRAINT fk_product_likes_user    FOREIGN KEY (user_id)    REFERENCES users(id),
    CONSTRAINT fk_product_likes_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE swatch_likes (
      id          BIGSERIAL PRIMARY KEY,
      user_id     BIGINT,
      swatch_id   BIGINT,
      created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

      CONSTRAINT uq_user_swatch_likes UNIQUE (user_id, swatch_id),
      CONSTRAINT fk_swatch_likes_user   FOREIGN KEY (user_id)   REFERENCES users(id),
      CONSTRAINT fk_swatch_likes_swatch FOREIGN KEY (swatch_id) REFERENCES swatches(id)
);

CREATE TABLE shoutouts (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT,
    user_id     BIGINT,
    content     TEXT,
    link_url    TEXT NULL,
    is_active   BOOLEAN DEFAULT TRUE,
    expires_at  TIMESTAMP NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_shoutouts_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_shoutouts_user    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE notifications (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT,
    type        VARCHAR(30),
    reference_id    INT,
    product_id      BIGINT NULL,
    message         TEXT,
    is_read         BOOLEAN DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_notifications_product FOREIGN KEY (product_id) REFERENCES products(id)
);