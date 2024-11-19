INSERT INTO historial_d
SELECT TICKER, 
PER, 
to_timestamp(concat(DATE, ' ', TIME), 'yyyyMMdd HHmmss') AS DATE, 
OPEN, 
HIGH, 
LOW, 
CLOSE, 
VOL, 
OPENINT 
FROM historial_raw_d;