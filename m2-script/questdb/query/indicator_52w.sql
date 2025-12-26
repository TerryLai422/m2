WITH first_stage AS
(SELECT 
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
FROM 
  historical_stock_d)
INSERT INTO 
  indicator_stock_52w
SELECT
    type, 
    date, 
    ticker, 
    high, 
    low, 
    close, 
    previous_close, 
    vol, 
    previous_vol,
    high52w, 
    previous_high52w, 
    (close - high52w)/high52w, 
    low52w, 
    previous_low52w, 
    (close - low52w)/low52w
FROM 
  first_stage;
