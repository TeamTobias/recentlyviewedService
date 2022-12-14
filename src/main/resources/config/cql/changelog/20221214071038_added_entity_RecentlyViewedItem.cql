
CREATE TABLE IF NOT EXISTS recentlyViewedItem (
    id uuid,
    userid bigint,
    catalogid bigint,
    brandname text,
    catalogname text,
    catalogunitprice int,
    catalogimg text,
    PRIMARY KEY(id)
);
