WITH total_counts AS (
    SELECT
        type, 
        date, 
        COUNT(*) AS total_count
    FROM indicators
    WHERE 
        type LIKE 'MA_%' 
    GROUP BY type, date
),
positive_difference_counts AS (
    SELECT 
        type,
        date, 
        COUNT(*) AS positive_count
    FROM indicators
    WHERE 
        type LIKE 'MA_%' 
        AND difference > 0
    GROUP BY type, date
)
SELECT 
    t.type,
    t.date,
    t.total_count,
    p.positive_count,
    (p.positive_count * 1.0 / t.total_count) * 100 AS percentage_positive
FROM total_counts t
JOIN positive_difference_counts p ON 
    t.type =p.type AND t.date = p.date
ORDER BY t.date desc, t.type