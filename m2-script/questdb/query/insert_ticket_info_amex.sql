INSERT INTO ticker_info
SELECT
    ticker,
    name,
    'AMEX',
    sector,
    industry,
    cap
from amex