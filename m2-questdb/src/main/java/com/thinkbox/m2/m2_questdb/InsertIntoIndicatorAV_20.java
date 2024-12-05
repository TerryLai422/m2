package com.thinkbox.m2.m2_questdb;

public class InsertIntoIndicatorAV_20 {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query = "INSERT INTO indicators\n" +
                "SELECT \n" +
                "    ticker, \n" +
                "    'AV_20', \n" +
                "    date, \n" +
                "    vol, \n" +
                "    count() \n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN UNBOUNDED PRECEDING AND 19 PRECEDING) \n" +
                "        AS 'valid',    \n" +
                "    first_value(vol) \n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) \n" +
                "        AS 'first',\n" +
                "    avg(vol)\n" +
                "        OVER \n" +
                "        (PARTITION BY ticker ORDER BY date \n" +
                "        ROWS BETWEEN 19 PRECEDING AND CURRENT ROW) \n" +
                "        AS 'value' \n" +
                "FROM historial_d;";

        ExecuteQuery.execute(url, query);;
    }
}