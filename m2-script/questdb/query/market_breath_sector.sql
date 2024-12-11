WITH total_counts AS (
    SELECT 
        i.type,
        i.date,
        ti.sector, 
        COUNT(*) AS total_count
    FROM indicators i
    JOIN ticker_info ti ON i.ticker = ti.ticker
    WHERE 
        type LIKE 'MA_%' 
    GROUP BY i.date, ti.sector
),
positive_difference_counts AS (
    SELECT
        i.type,
        i.date, 
        ti.sector,
        COUNT(*) AS positive_count
    FROM indicators i
    JOIN ticker_info ti ON i.ticker = ti.ticker
    WHERE 
        type LIKE 'MA_%' 
        AND difference > 0
    GROUP BY i.date, ti.sector
)
SELECT
    t.type,
    t.date,
    t.sector,
    t.total_count,
    p.positive_count,
    (p.positive_count * 1.0 / t.total_count) * 100 AS percentage_positive
FROM total_counts t
JOIN positive_difference_counts p ON 
    t.type = p.type AND t.date = p.date AND t.sector = p.sector
ORDER BY t.date, t.sector;