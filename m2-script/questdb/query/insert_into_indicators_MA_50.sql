INSERT INTO indicators
SELECT 
    ticker, 
    'MA_50', 
    date, 
    close, 
    avg(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) 
        AS 'value',    
    first_value(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) 
        AS 'first',
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 49 PRECEDING) 
        AS 'total',    
    0 AS difference,
    0 AS percentage
FROM historial_d;