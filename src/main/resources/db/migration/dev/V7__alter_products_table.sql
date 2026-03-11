ALTER TABLE products
    ADD COLUMN full_name    VARCHAR(200),
    ADD COLUMN full_name_ko VARCHAR(200);

ALTER TABLE products ADD COLUMN search_vector tsvector;

CREATE INDEX idx_products_search_vector ON products USING GIN(search_vector);

CREATE OR REPLACE FUNCTION update_search_vector()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.search_vector :=
            to_tsvector('simple', coalesce(NEW.full_name, '')) ||
            to_tsvector('simple', coalesce(NEW.full_name_ko, ''));
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_products_search_vector
    BEFORE INSERT OR UPDATE ON products
    FOR EACH ROW EXECUTE FUNCTION update_search_vector();