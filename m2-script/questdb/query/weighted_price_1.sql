SELECT 
    ticker,
    date_trunc('day', date),
    first(open) AS 'open',
    max(high) AS 'high',
    min(low) AS 'low',
    last(close) AS 'close',
    sum(vol) AS 'cum_vol',
    sum((open + high + low + close) / 4  * vol) AS 'cum_exec',
    sum((open + high + low + close) / 4  * vol) / sum(vol) AS 'vwap'
FROM 
    historical_stock_5m
WHERE
ticker = 'MNSO'