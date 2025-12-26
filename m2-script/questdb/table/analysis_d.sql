CREATE TABLE analysis_% (
  type SYMBOL,
  date TIMESTAMP,
  total LONG,
  count LONG,
  percentage DOUBLE
), INDEX (type CAPACITY 32) 
TIMESTAMP(date) PARTITION BY YEAR WAL;