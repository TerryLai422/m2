INSERT INTO indicators
SELECT 
    ticker, 
    'MA_200', 
    date, 
    close, 
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 199 PRECEDING) 
        AS 'valid',    
    first_value(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 199 PRECEDING AND CURRENT ROW) 
        AS 'first',
    avg(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 199 PRECEDING AND CURRENT ROW) 
        AS 'value',
    0 AS difference,
    0 AS percentage    
FROM historial_d;