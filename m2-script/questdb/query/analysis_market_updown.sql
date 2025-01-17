INSERT INTO analysis_market 
SELECT
    'up_down',
    date,
    COUNT(ticker) AS 'total',
    SUM(CASE WHEN close > previous_close THEN 1 ELSE 0 END) AS 'count',
    (SUM(CASE WHEN close > previous_close THEN 1 ELSE 0 END) * 1.0 / COUNT(ticker)) * 100 AS 'percentage'
FROM
  indicators_52w
WHERE 
previous_close <> null
GROUP BY type, date
ORDER BY type, date desc