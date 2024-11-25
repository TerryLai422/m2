SELECT 
    i1.TICKER, 
    i1.DATE,
    i1.CLOSE, 
    i1.TYPE AS 'type1', 
    i2.TYPE AS 'type2',
    i1.VALUE AS 'value1',
    i2.VALUE AS 'value2',
    i1.VALUE > i2.VALUE AS 'cross'
FROM 
    indicators i1, 
    indicators i2
WHERE 
    i1.TICKER = i2.TICKER 
    AND i1.DATE = i2.DATE
    AND i1.TYPE = 'MA_50' 
    AND i2.TYPE = 'MA_200'
    AND i1.VALID IS NOT NULL 
    AND i2.VALID IS NOT NULL