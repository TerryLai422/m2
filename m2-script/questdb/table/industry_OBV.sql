CREATE TABLE 'industry_OBV' ( 
	type SYMBOL,
	date TIMESTAMP,
	sector SYMBOL,
	industry SYMBOL,
	obv_cap DOUBLE,
	change DOUBLE,
	percent DOUBLE,
	count INT
) timestamp(date) PARTITION BY YEAR;