WITH first_stage AS
(
SELECT
  ticker,
  date,
  open,
  high,
  low,
  close,
  vol,
  (open + high + low + close) / 4 AS 'weighted_price',
  (case
    when (close - weighted_price) = 0 then 0
    else
      least(1, greatest(0.1, abs((close - (weighted_price))/(high - low))))
  end) AS 'rate',
  (case
    when close > (weighted_price) then
      round(vol * rate, 0)
    else
      case
        when close < (weighted_price) then
          round(-1 * vol * rate, 0)
        else 0
      end
  end) AS 'weighted_obv',
  (case
    when close > open then vol
    else
      case
        when close < open then -1 * vol
        else 0
      end
  end) AS 'classic_obv',
FROM
  historical_stock_5m_M
), second_stage AS
(
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
    first_stage
)
INSERT INTO obv_stock
SELECT
  'OBV'
  ticker,
  date,
  count,
  open,
  high,
  low,
  close,
  total_vol,
  weighted_obv,
  classic_obv,
  vwap,
  lag(weighted_obv) OVER
        (PARTITION BY ticker ORDER BY date) AS 'previous_weighted_obv',
  lag(classic_obv) OVER
        (PARTITION BY ticker ORDER BY date) AS 'previous_classic_obv',                
  lag(close) OVER
        (PARTITION BY ticker ORDER BY date) AS 'previous_close',
  lag(total_vol) OVER
        (PARTITION BY ticker ORDER BY date) AS 'previous_total_vol',
  lag(vwap) OVER
        (PARTITION BY ticker ORDER BY date) AS 'previous_vwap'
FROM
  second_stage