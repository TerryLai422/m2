SELECT 
    ticker, 
    first(date), 
    first(open), 
    max(high), 
    min(low), 
    last(close), 
    sum(vol), 
    year(date), 
    week_of_year(date) 
FROM historical_d 
GROUP BY 
    ticker, 
    year(date), 
    week_of_year(date) 
ORDER BY 
    year(date) ASC, 
    week_of_year(date) ASC