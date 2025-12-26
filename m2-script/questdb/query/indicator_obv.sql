WITH first_stage AS
(SELECT 
  ticker,
  date,
  open, 
  high,
  low,
  close,
  vol,
  (open + high + low + close) / 4 AS 'weighted_price' ,
FROM 
  historical_stock_5m_M
WHERE
  ticker = 'MNSO')
SELECT     
  ticker,
  date_trunc('day', date) AS 'date',
  count(*) AS counter,
  sum(vol) AS total_vol,
  sum(
    case 
      when close > weighted_price then vol 
      else 
        case 
          when close < weighted_price then -1 * vol 
          else 0 
        end
    end) AS 'obv'
FROM 
  first_stage 
ORDER BY
  date desc