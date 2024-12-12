package com.thinkbox.m2.m2_questdb;

public class InsertIntoIndicator {
    public static String url = "http://localhost:9000/exec";

    public static String queryTemplate = "WITH first_stage AS\n" +
            "(SELECT \n" +
            "    date, ticker, %s AS 'value1', \n" +
            "    avg(%s) OVER \n" +
            "        (PARTITION BY ticker ORDER BY date \n" +
            "        ROWS BETWEEN %d PRECEDING AND CURRENT ROW) \n" +
            "    AS 'value2',    \n" +
            "    count() OVER \n" +
            "        (PARTITION BY ticker ORDER BY date \n" +
            "        ROWS BETWEEN UNBOUNDED PRECEDING AND %d PRECEDING) \n" +
            "    AS 'total'\n" +
            "FROM historical_d),\n" +
            "second_stage AS\n" +
            "(SELECT     \n" +
            "    date, ticker, value1, value2, total,\n" +
            "    value1 - value2 AS 'difference',\n" +
            "    ((value1 - value2) / value2) * 100 AS 'percentage',\n" +
            "    first_value(value1 - value2) OVER \n" +
            "        (PARTITION BY ticker ORDER BY date \n" +
            "        ROWS 1 PRECEDING EXCLUDE CURRENT ROW) \n" +
            "    AS 'previous_difference'\n" +
            "FROM first_stage \n" +
            "WHERE TOTAL > 0), \n" +
            "third_stage AS\n" +
            "(SELECT\n" +
            "    date, ticker, value1, value2, total,\n" +
            "    difference, previous_difference, percentage,\n" +
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
            "    date, ticker, value1, value2, total,\n" +
            "    difference, previous_difference, percentage, trend,\n" +
            "    min(trend) OVER (PARTITION BY ticker ORDER BY date \n" +
            "        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)\n" +
            "    AS 'minimum_trend'\n" +
            "FROM third_stage),\n" +
            "fifth_stage AS\n" +
            "(SELECT\n" +
            "    '%s_%d' AS type, date, ticker, value1, value2, total,\n" +
            "    difference, previous_difference, percentage, trend, minimum_trend,\n" +
            "    (total + minimum_trend) AS 'trending'\n" +
            "FROM fourth_stage)\n" +
            "INSERT INTO %s\n" +
            "SELECT * FROM fifth_stage;";

    public static void execute(String type, String prefix, int interval, String tableName) {
        String query = String.format(queryTemplate, type, type, interval - 1, interval - 1, prefix, interval, tableName);
        ExecuteQuery.execute(url, query);
    }
}