CREATE TABLE 'market_OBV' ( 
	type SYMBOL,
	date TIMESTAMP,
	obv_cap DOUBLE,
	change DOUBLE,
	percent DOUBLE,
	count INT
) timestamp(date) PARTITION BY YEAR;