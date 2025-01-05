INSERT INTO indicators_52w
SELECT 
    'GENERAL' AS type,
    date, 
    ticker, 
    high,
    low,
    close, 
    first_value(close) OVER (
        PARTITION BY ticker        
        ORDER BY date
        ROWS 1 PRECEDING EXCLUDE CURRENT ROW
    ) AS 'previous_close', 
    vol,
    first_value(vol) OVER (
        PARTITION BY ticker        
        ORDER BY date
        ROWS 1 PRECEDING EXCLUDE CURRENT ROW
    ) AS 'previous_vol',
    max(high) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW
    ) AS '52wHigh',       
    max(high) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
    ) AS 'previous_52wHigh',
    min(low) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW
    ) AS '52wLow',       
    min(low) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
    ) AS 'previous_52wLow' 
FROM historical_d