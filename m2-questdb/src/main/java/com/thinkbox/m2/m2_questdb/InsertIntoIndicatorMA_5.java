package com.thinkbox.m2.m2_questdb;

public class InsertIntoIndicatorMA_5 {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query = "WITH first_stage AS\n" +
                "(SELECT \n" +
                "    date, \n" +
                "    ticker, \n" +
                "    close, \n" +
                "    avg(close) OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN 4 PRECEDING AND CURRENT ROW) \n" +
                "    AS 'value',    \n" +
                "    count() OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN UNBOUNDED PRECEDING AND 4 PRECEDING) \n" +
                "    AS 'total'\n" +
                "FROM historial_d),\n" +
                "second_stage AS\n" +
                "(SELECT     \n" +
                "    date,\n" +
                "    ticker,\n" +
                "    close,\n" +
                "    value,  \n" +
                "    total,\n" +
                "    close - value AS 'difference',\n" +
                "    ((close - value) / value) * 100 AS 'percentage',\n" +
                "    first_value(close - value) OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS 1 PRECEDING EXCLUDE CURRENT ROW) \n" +
                "    AS 'previous_difference'\n" +
                "FROM first_stage \n" +
                "WHERE TOTAL > 0), \n" +
                "third_stage AS\n" +
                "(SELECT\n" +
                "    date,\n" +
                "    ticker,\n" +
                "    close,\n" +
                "    value,\n" +
                "    total,\n" +
                "    difference,\n" +
                "    previous_difference,\n" +
                "    percentage,\n" +
                "    CASE\n" +
                "        WHEN difference >=0 and previous_difference >= 0 THEN total\n" +
                "        ELSE\n" +
                "            CASE\n" +
                "                WHEN difference < 0 and previous_difference < 0 THEN total\n" +
                "                ELSE (1 - total)\n" +
                "            END\n" +
                "    END AS 'trend'\n" +
                "FROM second_stage),\n" +
                "fourth_stage AS\n" +
                "(SELECT\n" +
                "    date,\n" +
                "    ticker,\n" +
                "    close,\n" +
                "    value,\n" +
                "    total,\n" +
                "    difference,\n" +
                "    previous_difference,\n" +
                "    percentage,\n" +
                "    trend,\n" +
                "    min(trend) OVER (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)\n" +
                "    AS 'minimum_trend'\n" +
                "FROM third_stage),\n" +
                "fifth_stage AS\n" +
                "(SELECT\n" +
                "    'MA_5' AS type,\n" +
                "    date,\n" +
                "    ticker,\n" +
                "    close,\n" +
                "    value,\n" +
                "    total,\n" +
                "    difference,\n" +
                "    previous_difference,\n" +
                "    percentage,\n" +
                "    trend,\n" +
                "    minimum_trend,\n" +
                "    (total + minimum_trend) AS 'trending'\n" +
                "FROM fourth_stage)\n" +
                "INSERT INTO indicators\n" +
                "SELECT * FROM fifth_stage;";

        ExecuteQuery.execute(url, query);;
    }
}