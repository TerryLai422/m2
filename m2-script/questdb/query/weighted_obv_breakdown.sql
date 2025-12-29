INSERT INTO obv_stock_5m_M
SELECT 
  ticker,
  date,
  open, 
  high,
  low,
  close,
  vol,
  (case 
    when close > ((open + high + low + close) / 4) then 
      round(vol * least(1, greatest(0.1, abs((close - ((open + high + low + close) / 4))/(high - low)))), 0)
    else 
      case 
        when close < ((open + high + low + close) / 4) then 
          round(-1 * vol * least(1, greatest(0.1, abs((close - ((open + high + low + close) / 4))/(high - low)))), 0)
        else 0 
      end
  end) AS 'weighted_obv',
  (case 
    when (close - (open + high + low + close) / 4) = 0 then 0
    else 
      least(1, greatest(0.1, abs((close - ((open + high + low + close) / 4))/(high - low))))
  end) AS 'rate',
  (open + high + low + close) / 4 AS 'weighted_price',
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
WHERE
  ticker = 'MNSO'
ORDER BY
  date desc