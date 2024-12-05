package com.thinkbox.m2.m2_questdb;

public class InsertIntoHistorial {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query = "INSERT INTO historial_d\n" +
                "SELECT \n" +
                "    replace(ticker, '.US', ''), \n" +
                "    to_timestamp(date, 'yyyyMMdd') AS date, \n" +
                "    open, \n" +
                "    high, \n" +
                "    low, \n" +
                "    close, \n" +
                "    vol\n" +
                "FROM historial_raw_d\n" +
                "WHERE date >= '19700101' \n" +
                "ORDER BY date ASC;";

        ExecuteQuery.execute(url, query);;
    }
}