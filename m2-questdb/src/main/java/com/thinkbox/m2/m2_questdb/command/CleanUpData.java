package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class CleanUpData {
    public static long run() {
        long start = System.currentTimeMillis();
        String url = "http://localhost:9000/exec";
        String query1 = "TRUNCATE TABLE historical_d;";
        ExecuteQuery.run(url, query1);
        String query2 = "TRUNCATE TABLE historical_raw_d";
        ExecuteQuery.run(url, query2);
//        String query3 = "TRUNCATE TABLE indicators";
//        ExecuteQuery.execute(url, query3);
        long end = System.currentTimeMillis();
        System.out.println("TOTAL TIME: " + (end - start));
        return (end - start);
    }
}