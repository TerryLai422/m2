INSERT INTO industry_info
SELECT DISTINCT sector, industry 
FROM raw_basic_info 
ORDER BY sector, industry