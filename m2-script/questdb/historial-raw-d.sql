CREATE TABLE 'historial-raw-d' (
  TICKER SYMBOL capacity 10000 CACHE,
  PER SYMBOL capacity 256 CACHE,
  DATE VARCHAR,
  TIME VARCHAR,
  OPEN DOUBLE,
  HIGH DOUBLE,
  LOW DOUBLE,
  CLOSE DOUBLE,
  VOL LONG,
  OPENINT INT
);