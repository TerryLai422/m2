CREATE TABLE 'country_OBV' ( 
	type SYMBOL,
	date TIMESTAMP,
	id INT,
	country SYMBOL,
	obv_cap DOUBLE,
	change DOUBLE,
	percent DOUBLE,
	count INT
) timestamp(date) PARTITION BY YEAR;