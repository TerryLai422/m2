WITH first_stage AS
(SELECT
  ticker,
  date,
  previous_close,
  close,
  vol,
  CASE
  WHEN previous_close >=0 and close > previous_close THEN vol
  ELSE
      CASE
          WHEN previous_close >= 0 and close < previous_close THEN -1 * vol
          ELSE 0
      END
  END AS 'daily_value'
FROM indicator_stock_d_52w),
second_stage AS 
(SELECT 
  date,
  ticker,
  vol,
  daily_value,
  sum(daily_value) OVER (
    PARTITION BY ticker ORDER BY date        
  ) AS 'obv',
  close,
  count() OVER (
    PARTITION BY ticker ORDER BY date        
  ) AS 'total',
  first_value(daily_value) OVER (
    PARTITION BY ticker ORDER BY date
    ROWS 1 PRECEDING EXCLUDE CURRENT ROW
  ) AS 'previous_difference'
FROM first_stage
), 
third_stage AS 
(SELECT 
  date,
  ticker,
  vol,
  obv AS 'value2',
  close AS 'value3',
  total,
  daily_value AS 'difference',
  previous_difference,
  (daily_value/obv) AS 'percentage',
  CASE
      WHEN daily_value >=0 and previous_difference >= 0 THEN total
      ELSE
          CASE
              WHEN daily_value < 0 and previous_difference < 0 THEN total
              ELSE (1 - total)
          END
  END AS 'trend'
FROM second_stage
), 
fourth_stage AS 
(SELECT
    date, ticker, vol, value2, total,
    difference, previous_difference, percentage, trend,
    min(trend) OVER (PARTITION BY ticker ORDER BY date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
    AS 'minimum_trend'
FROM third_stage)
SELECT 
  'OBV' AS 'type',
  date,
  ticker,
  vol AS 'value1',
  value2,
  value3,
  total,
  difference,
  previous_difference,
  percentage,
  trend,
  minimum_trend,
  (total + minimum_trend) AS 'trending'
FROM fourth_stage
ORDER BY date DESC