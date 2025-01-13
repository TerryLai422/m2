package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class CleanUpData {
    public static Object run(String url, String tablePrefix, String type) {
        String query2 = "TRUNCATE TABLE " + tablePrefix + type + ";";
        return ExecuteQuery.run(url, query2);
    }
}