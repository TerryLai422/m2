INSERT INTO 
    historical_5m_Z
SELECT 
    *
FROM
    historical_5m 
WHERE 
    ticker ~ '^[Z].+'
ORDER BY
    date, ticker