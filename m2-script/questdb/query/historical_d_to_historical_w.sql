WITH first_stage AS
(
SELECT 
  ticker,
  date, 
  datediff('d', date, '1971-01-04')/7 AS 'group_num', 
  open, 
  high, 
  low, 
  close, 
  vol
FROM 
  historical_stock_d 
)
INSERT INTO historical_stock_w
SELECT
    ticker, 
    first(date) AS 'date', 
    first(open), 
    max(high), 
    min(low), 
    last(close), 
    sum(vol)
FROM 
  first_stage
GROUP by 
  ticker, group_num