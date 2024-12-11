CREATE TABLE analysis_market (
  type SYMBOL,
  date TIMESTAMP,
  total LONG,
  above LONG,
  percentage DOUBLE
), INDEX (type CAPACITY 32) 
TIMESTAMP(date) PARTITION BY YEAR WAL;