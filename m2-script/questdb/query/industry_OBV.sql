WITH first_stage AS
(
SELECT
    type,
    date,
    info.sector,
    info.industry,
    value2 AS 'obv',
    difference AS 'daily_obv',
    value3 AS 'price'
FROM
    indicator_stock_d_OBV
JOIN
    raw_stock_info info
ON
  ticker
WHERE
  type = 'OBV'
)
INSERT INTO industry_OBV
SELECT
  type,
  date,
  sector,
  industry,
  sum(obv * price) AS 'obv_cap',
  sum(daily_obv * price) AS 'flow',
  sum(daily_obv * price) / sum(obv * price) AS 'percent',
  count(*) AS 'total'
FROM first_stage
WHERE
  industry IS NOT null AND sector IS NOT null