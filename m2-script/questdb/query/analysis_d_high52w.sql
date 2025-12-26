INSERT INTO 
    analysis_%_d 
SELECT
    'high52w' as 'type', 
    date, 
    count(ticker) as 'total',
    SUM(CASE WHEN high52w > previous_high52w THEN 1 ELSE 0 END) AS 'count',
    (SUM(CASE WHEN high52w > previous_high52w THEN 1 ELSE 0 END) * 1.0 / COUNT(ticker)) * 100 AS 'percentage'
FROM indicator_%_d_52w
WHERE 
previous_close <> null
ORDER BY type, date DESC