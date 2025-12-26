WITH first_stage AS
(SELECT
    i1.date, i1.ticker,
    i1.value2 AS 'value1',
    i2.value2 AS 'value2',
        count() OVER
        (PARTITION BY i2.ticker ORDER BY i2.date)
    AS 'total'
FROM %s i1
JOIN %s i2 ON i1.date = i2.date AND i1.ticker = i2.ticker
WHERE i1.type = 'MA_%s' and i2.type = 'MA_%s' %s),
second_stage AS
(SELECT
    date, ticker, value1, value2, total,
    value1 - value2 AS 'difference',
    ((value1 - value2) / value2) * 100 AS 'percentage',
    first_value(value1 - value2) OVER
        (PARTITION BY ticker ORDER BY date
        ROWS 1 PRECEDING EXCLUDE CURRENT ROW)
    AS 'previous_difference'
FROM first_stage
WHERE total > 0),
third_stage AS
(SELECT
    date, ticker, value1, value2, total,
    difference, previous_difference, percentage,
    CASE
        WHEN difference >=0 and previous_difference >= 0 THEN total
        ELSE
            CASE
                WHEN difference < 0 and previous_difference < 0 THEN total
                ELSE (1 - total)
            END
    END AS 'trend'
FROM second_stage),
fourth_stage AS
(SELECT
    date, ticker, value1, value2, total,
    difference, previous_difference, percentage, trend,
    min(trend) OVER (PARTITION BY ticker ORDER BY date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
    AS 'minimum_trend'
FROM third_stage)
INSERT INTO %s
SELECT
    '%s' AS type, date, ticker, value1, value2, total,
    difference, previous_difference, percentage, trend, minimum_trend,
    (total + minimum_trend) AS 'trending'
FROM fourth_stage