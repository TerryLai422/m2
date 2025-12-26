INSERT INTO
  analysis_%_d  
SELECT
    'up_down',
    date,
    COUNT(ticker) AS 'total',
    SUM(CASE WHEN close > previous_close THEN 1 ELSE 0 END) AS 'count',
    (SUM(CASE WHEN close > previous_close THEN 1 ELSE 0 END) * 1.0 / COUNT(ticker)) * 100 AS 'percentage'
FROM
  indicator_%_d_52w
WHERE 
previous_close <> null
GROUP BY type, date
ORDER BY type, date desc