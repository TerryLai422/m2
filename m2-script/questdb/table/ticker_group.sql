CREATE TABLE ticker_group (
  type SYMBOL CAPACITY 32 CACHE,
  date TIMESTAMP,
  ticker SYMBOL,
  close DOUBLE,
  previous_close DOUBLE,
  percentage_close DOUBLE,
  vol DOUBLE,
  av20d DOUBLE,
  percentage_vol DOUBLE
), 
INDEX(ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;