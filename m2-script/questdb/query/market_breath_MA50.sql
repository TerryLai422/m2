WITH total_counts AS (
    SELECT 
        date, 
        COUNT(*) AS total_count
    FROM indicators
    WHERE 
        type = 'MA_50' 
        AND DATE_TRUNC('month', date) = '2024-12-01'
    GROUP BY date
),
positive_difference_counts AS (
    SELECT 
        date, 
        COUNT(*) AS positive_count
    FROM indicators
    WHERE 
        type = 'MA_50' 
        AND DATE_TRUNC('month', date) = '2024-12-01'
        AND difference > 0
    GROUP BY date
)
SELECT 
    t.date,
    t.total_count,
    p.positive_count,
    (p.positive_count * 1.0 / t.total_count) * 100 AS percentage_positive
FROM total_counts t
JOIN positive_difference_counts p ON t.date = p.date
ORDER BY t.date;