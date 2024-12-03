CREATE TABLE ticker_info (
  ticker SYMBOL,
  name VARCHAR,
  exchange SYMBOL CAPACITY 10,
  sector SYMBOL CAPACITY 20,
  industry SYMBOL CAPACITY 200,
  market_cap VARCHAR
), INDEX (ticker CAPACITY 9000);