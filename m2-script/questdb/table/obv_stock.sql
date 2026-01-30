CREATE TABLE 'obv_stock' ( 
	ticker SYMBOL INDEX CAPACITY 16384,
	date TIMESTAMP,
	counter INT,
	open DOUBLE,
	high DOUBLE,
	low DOUBLE,
	close DOUBLE,
	total_vol DOUBLE,
	weighted_obv DOUBLE,
	classic_obv DOUBLE,
	vwap DOUBLE
) timestamp(date) PARTITION BY YEAR;