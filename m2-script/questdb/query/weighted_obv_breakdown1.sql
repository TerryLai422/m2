INSERT INTO obv_stock_5m_M
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
WHERE
  ticker = 'MNSO'
ORDER BY
  date DESC