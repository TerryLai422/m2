SELECT 
    i1.ticker, 
    i1.date,
    i1.close, 
    i1.type AS 'type1', 
    i2.type AS 'type2',
    i1.value AS 'value1',
    i2.value AS 'value2',
    CASE  
        WHEN i1.value > i2.value THEN 'A'
        ELSE 'B'
    END AS 'cross'
FROM 
    indicators i1, 
    indicators i2
WHERE 
    i1.ticker = i2.ticker 
    AND i1.date = i2.date
    AND i1.type = 'MA_50' 
    AND i2.type = 'MA_200'
    AND i1.total > 0
    AND i2.total > 0