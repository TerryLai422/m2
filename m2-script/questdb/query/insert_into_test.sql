insert into test VALUES
('2024-09-12T00:00:00.000000Z', -4, 0)


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
FROM historical_d
WHERE ticker = 'AAPL'
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
    ((close - value) / value) * 100 AS 'percentage',
    first_value(close - value) OVER (
        PARTITION BY ticker        
        ORDER BY date
        ROWS 1 PRECEDING EXCLUDE CURRENT ROW
    ) AS price_from_1_row_before,
FROM first_stage 
)
SELECT * FROM second_stage
WHERE TOTAL > 0;