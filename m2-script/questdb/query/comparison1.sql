SELECT 
    i1.ticker, 
    i1.date,
    i1.close, 
    i1.TYPE AS 'type1', 
    i2.TYPE AS 'type2',
    i1.VALUE AS 'value1',
    i2.VALUE AS 'value2',
    CASE
        WHEN i1.VALUE IS NULL OR I2.VALUE IS NULL THEN 'NA' 
        ELSE
            CASE  
                WHEN i1.VALUE > i2.VALUE THEN 'A'
                ELSE 'B'
            END
    END AS 'cross'
from indicators i1, indicators i2
where 
i1.ticker = i2.ticker and i1.date = i2.date
and i1.type = 'MA_50' and i2.type = 'MA_200'
ORDER BY i1.date DESC