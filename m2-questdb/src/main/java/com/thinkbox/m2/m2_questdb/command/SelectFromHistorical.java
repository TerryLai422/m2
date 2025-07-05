package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class SelectFromHistorical implements Constants {
    public static Object run(String url, String type) {
        String query = "SELECT CAST(TO_STR(MIN(date), 'yyyyMMdd') AS INT) AS MIN, CAST(TO_STR(MAX(date), 'yyyyMMdd') AS INT) AS MAX FROM historical_" + type + ";";
        return ExecuteQuery.run(url, query);
    }
}