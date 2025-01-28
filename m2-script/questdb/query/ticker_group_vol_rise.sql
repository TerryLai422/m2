with first_step as 
(select *, (vol - average_vol)/average_vol as vol_change from indicator_av 
where 
date = '2025-01-27T00:00:00.000000Z' 
order by vol_change desc)
insert into ticker_group
select 'AI-VOL-RISE', f.date, f.ticker, w.close, w.previous_close, (w.close - w.previous_close)/w.previous_close as percentage_close, f.vol, f.average_vol, f.vol_change
from first_step f
join indicator_52w w on f.ticker = w.ticker and f.date = w.date and ((w.close - w.previous_close)/w.previous_close) > 0
where f.vol_change > 1