INSERT INTO indicators
SELECT 
    ticker, 
    'AV_20', 
    date, 
    vol, 
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 19 PRECEDING) 
        AS 'valid',    
    first_value(vol) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS 'first',
    avg(vol)
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS 'value' 
FROM historial_d;