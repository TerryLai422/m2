CREATE TABLE 'obv_stock' (
	type SYMBOL,
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
	vwap DOUBLE,
	previous_close DOUBLE,
	previous_total_vol DOUBLE,
	previous_weighted_obv DOUBLE,
	previous_classic_obv DOUBLE,
	previous_vwap DOUBLE	
) timestamp(date) PARTITION BY YEAR;