CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- 검색 성능을 위한 인덱스
CREATE INDEX idx_products_name_trgm ON products USING gin (name gin_trgm_ops);
CREATE INDEX idx_products_name_ko_trgm ON products USING gin (name_ko gin_trgm_ops);