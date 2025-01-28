WITH first_stage AS
(SELECT 
ticker,
date,
open, 
high,
low,
close,
vol,
(high + low + close) / 3 AS 'tp'
FROM historical_5m
WHERE
ticker = 'MNSO')
SELECT * FROM first_stage