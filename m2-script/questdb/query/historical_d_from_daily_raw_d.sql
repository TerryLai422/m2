INSERT INTO historical_d
SELECT 
    replace(d.ticker, '.US', '') AS t1, 
    to_timestamp(d.date, 'yyyyMMdd') AS d1, 
    d.open, 
    d.high, 
    d.low, 
    d.close, 
    d.vol
FROM daily_raw_d d
JOIN ticker_list l
ON l.ticker = replace(d.ticker, '.US', '')
AND datediff('d', to_timestamp(d.date, 'yyyyMMdd'), '2024-12-10') > 0 