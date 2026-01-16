WITH first_stage AS
(
SELECT FROM historical_d 
    ticker,
    FIRST_VALUE(date) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'first',
    LAST_VALUE(date) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'last',    
    FIRST_VALUE(open) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'open',    
    MAX(high) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'high',
    MIN(low) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'low',
    close,
    sum(vol) OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'vol',
    COUNT() OVER 
    (PARTITION BY ticker ORDER BY date 
    RANGE BETWEEN '4' day PRECEDING AND CURRENT ROW)
    AS 'count'
FROM historical_stock_d 
ORDER BY 
    first DESC
)
INSERT INTO historical_stock_w
SELECT
    ticker,
    MIN(first) AS 'date',
    FIRST(open) AS 'open',
    MAX(high) AS 'high',
    MIN(low) AS 'low',
    LAST(close) AS 'close',
    MAX(vol) AS 'vol'
FROM first_stage 
WHERE 
    WEEK_OF_YEAR(first) = WEEK_OF_YEAR(last) 
GROUP BY 
    ticker, YEAR(first), WEEK_OF_YEAR(first)