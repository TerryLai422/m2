WITH stage_one AS
(
SELECT
  ri.ticker,
  cc.id,
  cc.country
FROM
    raw_stock_info ri
JOIN
    country cc
ON
    country
WHERE
    ri.country IS NOT NULL
), stage_two AS
(
SELECT
    type,
    date,
    id,
    country,
    value2 AS 'obv',
    difference AS 'daily_obv',
    value3 AS 'price'
FROM
    indicator_stock_d_OBV
JOIN
    stage_one
ON
  ticker
WHERE
  type = 'OBV'
)
INSERT INTO country_OBV
SELECT
    type,
    date,
    id,
    country,
    sum(obv * price) AS 'obv_cap',
    sum(daily_obv * price) AS 'change',
    sum(daily_obv * price) / sum(obv * price) AS 'percent',
    count(*) AS 'total'
FROM stage_two