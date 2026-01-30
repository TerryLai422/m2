WITH stage_one AS
(
SELECT
  ri.ticker,
  ii.id,
  ii.sector,
  ii.industry
FROM
  raw_stock_info ri
JOIN
  industry_info ii
ON
  ri.sector = ii.sector AND ri.industry = ii.industry
), stage_two AS
(
SELECT
  type,
  date,
  id,
  sector,
  industry,
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
INSERT INTO industry_OBV
SELECT
  type,
  date,
  id,
  sector,
  industry,
  sum(obv * price) AS 'obv_cap',
  sum(daily_obv * price) AS 'change',
  sum(daily_obv * price) / sum(obv * price) AS 'percent',
  count(*) AS 'total'
FROM stage_two