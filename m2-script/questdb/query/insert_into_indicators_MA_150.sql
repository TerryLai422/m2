INSERT INTO indicators
SELECT 
    ticker, 
    'MA_150', 
    date, 
    close, 
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 149 PRECEDING) 
        AS 'valid',    
    first_value(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 149 PRECEDING AND CURRENT ROW) 
        AS 'first',
    avg(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 149 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;