WITH first_stage AS
(SELECT 
ticker,
date,
open, 
high,
low,
close,
vol,
(high + low + close) / 3 AS 'tp'
FROM historical_5m_M
WHERE
ticker = 'MNSO')
SELECT     
ticker,
date_trunc('day', date) AS date,
count(*) AS counter,
sum(vol) AS total_vol,
sum(
  case 
    when close > tp then vol 
    else 
      case 
        when close < tp then -1 * vol 
        else 0 
      end
  end) AS obv
FROM first_stage order by date desc