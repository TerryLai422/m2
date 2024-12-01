INSERT INTO indicators
SELECT 
    ticker, 
    'MA_5', 
    date, 
    close, 
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 4 PRECEDING) 
        AS 'valid',    
    first_value(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS 'first',
    avg(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS 'value' 
FROM historial_d;