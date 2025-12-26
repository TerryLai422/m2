CREATE TABLE indicator_stock_d_MA (
  type SYMBOL CAPACITY 32 CACHE,
  date TIMESTAMP,
  ticker SYMBOL,
  value1 DOUBLE,
  value2 DOUBLE,
  total INT,
  difference DOUBLE,
  previous_difference DOUBLE,
  percentage DOUBLE,
  trend LONG,
  minimum_trend DOUBLE,
  trending DOUBLE
), 
INDEX(ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;