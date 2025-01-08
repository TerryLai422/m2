package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class SelectFromHistorial_5m implements Constants {
    public static long run(String url, String type) {
        String query = "SELECT CAST(TO_STR(MAX(date), 'yyyyMMdd') AS INT) AS MAX FROM historical_5m;";
        return ExecuteQuery.run(url, query);
    }
}