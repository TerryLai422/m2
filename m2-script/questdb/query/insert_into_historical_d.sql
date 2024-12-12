INSERT INTO historical_d
SELECT 
    replace(ticker, '.US', ''), 
    to_timestamp(date, 'yyyyMMdd') AS date, 
    open, 
    high, 
    low, 
    close, 
    vol
FROM historical_raw_d
WHERE date >= '19700101' 
ORDER BY date ASC;