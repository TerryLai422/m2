INSERT INTO ticker_info
SELECT
    ticker,
    name,
    'NASDAQ',
    sector,
    industry,
    cap
from nasdaq