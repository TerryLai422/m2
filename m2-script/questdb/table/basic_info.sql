CREATE TABLE basic_info (
  ticker SYMBOL,
  name VARCHAR,
  market_cap DOUBLE,
  market_cap_currencey VARCHAR,
  sector SYMBOL CAPACITY 20,
  industry SYMBOL CAPACITY 200,
  exchange SYMBOL CAPACITY 10,
  country SYMBOL CAPACITY 300,
  float_shares_outstanding DOUBLE,
  float_shares_percentage DOUBLE
), INDEX (ticker CAPACITY 9000);