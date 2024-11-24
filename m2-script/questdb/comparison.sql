SELECT 
    i1.TICKER, 
    i1.DATE,
    i1.CLOSE, 
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
i1.TICKER = i2.TICKER and i1.DATE = i2.DATE
and i1.TYPE = 'MA_5' and i2.TYPE = 'MA_10'