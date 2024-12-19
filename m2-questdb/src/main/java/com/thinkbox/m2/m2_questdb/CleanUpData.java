package com.thinkbox.m2.m2_questdb;

public class CleanUpData {
    public static void main(String[] args) {
        String url = "http://localhost:9000/exec";
        String query1 = "TRUNCATE TABLE historial_d;";
        ExecuteQuery.execute(url, query1);
        String query2 = "TRUNCATE TABLE historial_raw_d";
        ExecuteQuery.execute(url, query2);
//        String query3 = "TRUNCATE TABLE indicators";
//        ExecuteQuery.execute(url, query3);
    }
}