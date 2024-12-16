INSERT INTO ticker_list
SELECT 
    ticker, 
    max(date) AS 'date'
FROM historical_d
ORDER BY ticker
