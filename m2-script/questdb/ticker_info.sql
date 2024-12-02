CREATE TABLE ticker_info (
  ticker SYMBOL,
  name VARCHAR,
  sector SYMBOL CAPACITY 20,
  industy SYMBOL CAPACITY 200,
  cap VARCHAR
), INDEX (ticker CAPACITY 9000);