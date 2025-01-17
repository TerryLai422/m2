INSERT INTO indicator_52w
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
    ) AS 'high52w',       
    max(high) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
    ) AS 'previous_high52w',
    min(low) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW
    ) AS 'low52w',       
    min(low) OVER (
      PARTITION BY ticker        
        ORDER BY date
        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
    ) AS 'previous_low52w' 
FROM historical_d