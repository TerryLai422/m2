CREATE TABLE indicators (
  ticker SYMBOL,
  type SYMBOL CAPACITY 32 CACHE,
  date TIMESTAMP,
  close DOUBLE,
  total SHORT,
  first DOUBLE,
  value DOUBLE,
  difference DOUBLE,
  percentage DOUBLE
), 
INDEX(ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;