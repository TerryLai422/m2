WITH stage_one AS
(
SELECT DISTINCT
  sector,
  industry
FROM
  raw_stock_info
WHERE
  sector IS NOT NULL AND industry IS NOT NULL
ORDER BY
  sector,
  industry
)
INSERT INTO industry_info
SELECT
  row_number() OVER () AS 'id',
  sector,
  industry
FROM
  stage_one