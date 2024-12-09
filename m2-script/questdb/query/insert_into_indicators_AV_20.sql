INSERT INTO indicators
SELECT 
    ticker, 
    'AV_20', 
    date, 
    vol, 
    avg(vol)
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS 'value',    
    first_value(vol) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS 'first',
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 19 PRECEDING) 
        AS 'total',   
    0 AS difference,
    0 AS percentage 
FROM historial_d;