INSERT INTO
    obv_cap_info
SELECT
    type,
    date, 
    sum(obv_cap) AS 'obv_cap', 
    sum(change) AS 'change', 
    sum(change) / sum(obv_cap) AS 'percent',
    count() AS 'count' from industry_OBV 