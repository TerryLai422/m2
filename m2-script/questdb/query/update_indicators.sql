UPDATE indicators
SET 
    difference = close - value,
    percentage = ((close - value) / value) * 100
WHERE total > 0;