CREATE TABLE analysis_sector (
  type SYMBOL,
  date TIMESTAMP,
  sector SYMBOL,
  total LONG,
  above LONG,
  percentage DOUBLE
), INDEX (sector CAPACITY 20) 
TIMESTAMP(date) PARTITION BY YEAR WAL;