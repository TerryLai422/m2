INSERT INTO obv_stock
SELECT 
    ticker,
    date_trunc('day', date) AS 'date',
    count() AS 'count',
    first(open) AS 'open',
    max(high) AS 'high',
    min(low) AS 'low',
    last(close) AS 'close',
    sum(vol) AS 'total_vol',
    sum(weighted_obv) AS 'weighted_obv',
    sum(classic_obv) AS 'classic_obv',
    round(sum(weighted_price  * vol) / sum(vol), 4) AS 'vwap'
FROM 
    obv_stock_5m_M
WHERE
    ticker = 'MNSO'