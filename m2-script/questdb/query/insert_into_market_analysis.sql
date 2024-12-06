INSERT INTO market_analysis 
SELECT
    type,
    date,
    COUNT(ticker) AS total_ticker_count,
    SUM(CASE WHEN close > value THEN 1 ELSE 0 END) AS ticker_count_greater_than_value,
    (SUM(CASE WHEN close > value THEN 1 ELSE 0 END) * 1.0 / COUNT(ticker)) * 100 AS percentage_greater_than_value
FROM
  indicators
WHERE 
type LIKE 'MA_%' 
AND total > 0
GROUP BY type, date
ORDER BY date