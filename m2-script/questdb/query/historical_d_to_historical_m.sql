INSERT INTO
  historical_stock_m
SELECT 
  ticker,
  first(date) AS 'date', 
  first(open), 
  max(high), 
  min(low), 
  last(close), 
  sum(vol) 
FROM 
  historical_stock_d 
GROUP BY
  ticker, year(date), month(date)  
