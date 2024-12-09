CREATE TABLE indicators (
  type SYMBOL CAPACITY 32 CACHE,
  date TIMESTAMP,
  ticker SYMBOL,
  close DOUBLE,
  value DOUBLE,
  first DOUBLE,
  total SHORT,
  difference DOUBLE,
  percentage DOUBLE
), 
INDEX(ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;