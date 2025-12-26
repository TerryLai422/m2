INSERT INTO historical_d
SELECT 
    replace(ticker, '.US', ''), 
    CASE
      WHEN per = 'D' THEN 
        to_timestamp(date, 'yyyyMMdd')
      ELSE
        dateadd('h', -6, to_timestamp(concat(date,'T',time), 'yyyyMMddTHHmmss'))    
    END AS 'date',
    open, 
    high, 
    low, 
    close, 
    vol
FROM 
  historical_raw_d
WHERE 
  date >= '19700101' 
ORDER BY
  date ASC