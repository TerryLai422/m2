package com.thinkbox.m2.m2_questdb;

public class InsertIntoHistorial implements Constants {
    public static long run() {
        String query = "INSERT INTO historical_d\n" +
                "SELECT \n" +
                "    replace(ticker, '.US', ''), \n" +
                "    to_timestamp(date, 'yyyyMMdd') AS date, \n" +
                "    open, \n" +
                "    high, \n" +
                "    low, \n" +
                "    close, \n" +
                "    vol\n" +
                "FROM historical_raw_d\n" +
                "WHERE date >= '19700101' \n" +
                "ORDER BY date ASC;";
        String url = String.format(execUrlTemplate, hostName);
        return ExecuteQuery.run(url, query);
    }
}