CREATE TABLE historical_5m_A (
  ticker SYMBOL,
  date TIMESTAMP,
  open DOUBLE,
  high DOUBLE,
  low DOUBLE,
  close DOUBLE,
  vol DOUBLE
), 
INDEX (ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;