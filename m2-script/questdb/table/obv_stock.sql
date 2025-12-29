CREATE TABLE 'obv_stock' ( 
	ticker SYMBOL CAPACITY 16384 CACHE INDEX CAPACITY 16384,
	date TIMESTAMP,
	counter INT,
	open DOUBLE,
	close DOUBLE,
	total_vol DOUBLE,
	weighted_obv DOUBLE,
	classic_obv DOUBLE,
	vwap DOUBLE
) timestamp(date) PARTITION BY YEAR WAL
WITH maxUncommittedRows=500000, o3MaxLag=600000000us;