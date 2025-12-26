CREATE TABLE historical_%_w (
  ticker SYMBOL,
  date TIMESTAMP,
  open DOUBLE,
  high DOUBLE,
  low DOUBLE,
  close DOUBLE,
  vol DOUBLE,
  year INT,
  week INT
), 
INDEX (ticker CAPACITY 9000) 
TIMESTAMP(date) PARTITION BY YEAR WAL;