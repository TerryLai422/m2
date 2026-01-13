CREATE TABLE 'industry_info' ( 
	sector SYMBOL CAPACITY 32 CACHE,
	industry SYMBOL CAPACITY 256 CACHE
)
WITH maxUncommittedRows=500000, o3MaxLag=600000000us;