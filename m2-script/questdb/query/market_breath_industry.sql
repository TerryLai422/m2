WITH total_counts AS (
    SELECT
        i.type,
        i.date,
        ti.industry, 
        COUNT(*) AS total_count
    FROM indicators_MA i
    JOIN ticker_info ti ON i.ticker = ti.ticker
    WHERE 
        type LIKE 'MA_%' 
    GROUP BY i.date, ti.industry
),
positive_difference_counts AS (
    SELECT
        i.type,
        i.date, 
        ti.industry,
        COUNT(*) AS positive_count
    FROM indicators_MA i
    JOIN ticker_info ti ON i.ticker = ti.ticker
    WHERE 
        type LIKE 'MA_%' 
        AND difference > 0
    GROUP BY i.date, ti.industry
)
SELECT 
    t.date,
    t.industry,
    t.total_count,
    p.positive_count,
    (p.positive_count * 1.0 / t.total_count) * 100 AS percentage_positive
FROM total_counts t
JOIN positive_difference_counts p ON 
    t.type = p.type AND t.date = p.date AND t.industry = p.industry
ORDER BY t.date, t.industry;