INSERT INTO historial_d
SELECT 
    replace(ticker, '.US', ''), 
    to_timestamp(date, 'yyyyMMdd') AS date, 
    open, 
    high, 
    low, 
    close, 
    vol
FROM historial_raw_d
WHERE date >= '19700101' 
ORDER BY date ASC;