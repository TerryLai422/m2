SELECT 
    i1.date,
    i1.ticker,
    i1.value2 AS 'value1', 
    i2.value2 AS 'value2',
FROM 
    indicators_stock_d_MA i1
JOIN 
    indicators_stock_d_MA i2 
ON 
    i1.date = i2.date 
AND i1.ticker = i2.ticker
WHERE 
    i1.type = 'MV_20' 
AND i2.type = 'MV_50'
AND i1.ticker = 'AAPL' 
ORDER BY date desc