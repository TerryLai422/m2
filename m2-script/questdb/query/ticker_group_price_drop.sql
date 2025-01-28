with first_step as 
(select *, (close - previous_close)/previous_close as change from indicator_52w 
where 
date = '2025-01-27T00:00:00.000000Z' 
and
previous_close != null
order by change asc)
insert into ticker_group
select 'AI-DROPOFF', f.date, f.ticker, f.close, f.previous_close, f.change, f.vol, av.average_vol, f.vol/av.average_vol 
from first_step f
join indicator_AV av on f.ticker = av.ticker and f.date = av.date
where f.change < -0.1 and av.vol > av.average_vol