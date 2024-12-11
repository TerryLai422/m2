SELECT 
    i1.date, i1.ticker, 
    i1.value2 AS 'value1', 
    i2.value2 AS 'value2',
FROM indicators i1
JOIN indicators i2 ON i1.date = i2.date AND i1.ticker = i2.ticker
WHERE i1.type = 'MV_20' and i2.type = 'MV_50'
AND i1.ticker = 'AAPL' order by date desc