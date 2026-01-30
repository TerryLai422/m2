WITH stage_one AS
(SELECT
    DISTINCT country
FROM
    raw_stock_info
WHERE
    country IS NOT NULL
ORDER BY
    country
)
INSERT INTO country
SELECT
    row_number() OVER () AS 'id',
    country
FROM
    stage_one