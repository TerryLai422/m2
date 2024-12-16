CREATE TABLE ticker_list (
  ticker SYMBOL,
  date TIMESTAMP
), INDEX (ticker CAPACITY 9000);