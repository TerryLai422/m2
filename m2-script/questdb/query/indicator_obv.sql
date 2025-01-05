SELECT 
    ticker,
    date_trunc('day', date),
    count(*) AS counter,
    sum(
        case 
        when close > open then vol 
        else 
            case 
            when close < open then -1 * vol 
            else 0 
            end
        end) AS obv
FROM historical_5m
WHERE
ticker = 'MNSO'