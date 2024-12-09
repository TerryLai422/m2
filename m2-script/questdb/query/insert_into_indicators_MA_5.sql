WITH first_stage AS
(SELECT 
    ticker, 
    'MA_5' AS type, 
    date, 
    close, 
    avg(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS 'value',    
    first_value(close) 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS 'first',
    count() 
        OVER 
        (PARTITION BY ticker ORDER BY date 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 4 PRECEDING) 
        AS 'total'
FROM historial_d
),
second_stage AS
(SELECT     
    type,
    date,
    ticker,
    close,
    value,
    first,    
    total,
    close - value AS 'difference',
    ((close - value) / value) * 100 AS 'percentage'
FROM first_stage 
)
INSERT INTO indicators
SELECT * FROM second_stage;