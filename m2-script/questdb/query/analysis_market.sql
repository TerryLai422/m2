INSERT INTO analysis_market 
SELECT
    type,
    date,
    COUNT(ticker) AS 'total',
    SUM(CASE WHEN difference > 0 THEN 1 ELSE 0 END) AS 'count',
    (SUM(CASE WHEN difference > 0 THEN 1 ELSE 0 END) * 1.0 / COUNT(ticker)) * 100 AS 'percentage'
FROM
  indicator_MA
WHERE 
type LIKE 'MA_%' 
AND total > 0
GROUP BY type, date
ORDER BY type, date desc