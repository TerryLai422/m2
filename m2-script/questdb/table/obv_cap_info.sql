CREATE TABLE 'obv_cap_info' ( 
	type SYMBOL,
	date TIMESTAMP,
	obv_cap DOUBLE,
	change DOUBLE,
	percent DOUBLE,
	count INT
) timestamp(date) PARTITION BY YEAR;