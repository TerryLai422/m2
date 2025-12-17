CREATE TABLE indicator_%_d_AV (
  type SYMBOL CAPACITY 32 CACHE,
  date TIMESTAMP,
  ticker SYMBOL,
  vol DOUBLE,
  average_vol DOUBLE,
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