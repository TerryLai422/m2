CREATE TABLE 'obv_stock_5m_M' ( 
	ticker SYMBOL CAPACITY 16384 CACHE INDEX CAPACITY 16384,
	date TIMESTAMP,
	open DOUBLE,
	high DOUBLE,
	low DOUBLE,
	close DOUBLE,
	vol DOUBLE,
	weighted_price DOUBLE,
	rate DOUBLE,
  weighted_obv DOUBLE,
	classic_obv DOUBLE
) timestamp(date) PARTITION BY YEAR WAL
WITH maxUncommittedRows=500000, o3MaxLag=600000000us;