INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_5', 
    DATE, 
    CLOSE, 
    sum(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 4 PRECEDING) 
        AS 'VALID',    
    first_value(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS "FIRST",
    avg(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_20', 
    DATE, 
    CLOSE, 
    sum(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 19 PRECEDING) 
        AS 'VALID',    
    first_value(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS "FIRST",
    avg(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_50', 
    DATE, 
    CLOSE, 
    sum(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 49 PRECEDING) 
        AS 'VALID',    
    first_value(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) 
        AS "FIRST",
    avg(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_150', 
    DATE, 
    CLOSE, 
    sum(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 149 PRECEDING) 
        AS 'VALID',    
    first_value(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 149 PRECEDING AND CURRENT ROW) 
        AS "FIRST",
    avg(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 149 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;

INSERT INTO indicators
SELECT 
    TICKER, 
    'MA_200', 
    DATE, 
    CLOSE, 
    sum(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN UNBOUNDED PRECEDING AND 199 PRECEDING) 
        AS 'VALID',    
    first_value(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 199 PRECEDING AND CURRENT ROW) 
        AS "FIRST",
    avg(CLOSE) 
        OVER 
        (PARTITION BY TICKER, PER ORDER BY DATE 
        ROWS BETWEEN 199 PRECEDING AND CURRENT ROW) 
        AS "VALUE" 
FROM historial_d;