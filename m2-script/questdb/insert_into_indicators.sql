INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_5', 
    DATE, 
    CLOSE, 
    avg(close) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 4 PRECEDING) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_20', 
    DATE, 
    CLOSE, 
    avg(close) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 19 PRECEDING) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_50', 
    DATE, 
    CLOSE, 
    avg(close) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 49 PRECEDING) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_150', 
    DATE, 
    CLOSE, 
    avg(close) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 149 PRECEDING) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_200', 
    DATE, 
    CLOSE, 
    avg(close) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 199 PRECEDING) 
        AS "VALUE" 
FROM historial_d;