INSERT INTO market_OBV
SELECT
    type,
    date,
    sum(value2 * value3) AS 'obv_cap',
    sum(difference * value3) AS 'change',
    sum(difference * value3) / sum(value2 * value3) AS 'percent',
    count(*) AS 'total'  
FROM 
    indicator_stock_d_OBV
WHERE
    type = 'OBV'