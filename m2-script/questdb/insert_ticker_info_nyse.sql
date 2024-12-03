INSERT INTO ticker_info
SELECT
    ticker,
    name,
    'NYSE',
    sector,
    industry,
    cap
from nyse