CREATE TABLE indicator_52w (
  type SYMBOL,
  date TIMESTAMP,
  ticker SYMBOL,
  high DOUBLE,
  low DOUBLE,
  close DOUBLE,
  previous_close DOUBLE,
  vol DOUBLE,
  previous_vol DOUBLE,
  52wHigh DOUBLE,
  previous_52wHigh DOUBLE,
  52wLow DOUBLE,
  previous_52wLow DOUBLE
), 
INDEX(ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;