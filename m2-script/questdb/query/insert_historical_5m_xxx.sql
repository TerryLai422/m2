INSERT INTO historical_5m_Z
SELECT * from historical_5m 
WHERE ticker ~ '^[Z].+'
ORDER BY date, ticker