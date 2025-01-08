SELECT 
    ticker,
    date_trunc('day', date),
    first(open) AS 'open',
    max(high) AS 'high',
    min(low) AS 'low',
    last(close) AS 'close',
    sum(vol) AS 'cum_vol',
    sum((high + low + close) / 3  * vol) AS 'cum_exec',
    sum((high + low + close) / 3  * vol) / sum(vol) AS 'vwap'
FROM historical_5m
WHERE
ticker = 'MNSO'