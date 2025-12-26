WITH first_stage AS
(SELECT 
    ticker,
    date,
    open, 
    high,
    low,
    close,
    vol,
    (open + high + low + close) / 4 AS 'weighted_price'
FROM 
    historical_stock_5m
WHERE
    ticker = 'MNSO')
SELECT * FROM first_stage