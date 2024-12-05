package com.thinkbox.m2.m2_questdb;

public class InsertIntoIndicatorMA_50 {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query = "INSERT INTO indicators\n" +
                "SELECT \n" +
                "    ticker, \n" +
                "    'MA_50', \n" +
                "    date, \n" +
                "    close, \n" +
                "    count() \n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN UNBOUNDED PRECEDING AND 49 PRECEDING) \n" +
                "        AS 'valid',    \n" +
                "    first_value(close) \n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) \n" +
                "        AS 'first',\n" +
                "    avg(close)\n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN 49 PRECEDING AND CURRENT ROW) \n" +
                "        AS 'value' \n" +
                "FROM historial_d;";

        ExecuteQuery.execute(url, query);;
    }
}