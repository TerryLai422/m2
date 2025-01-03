package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class InsertIntoHistorial implements Constants {
    public static long run(String url, String type) {
        String query = "INSERT INTO historical_" + type + "\n" +
                "SELECT \n" +
                "    replace(ticker, '.US', ''), \n" +
                "    CASE WHEN per = 'D' THEN \n" +
                "    to_timestamp(date, 'yyyyMMdd') \n" +
                "    ELSE dateadd('h', -6, to_timestamp(concat(date,'T',time), 'yyyyMMddTHHmmss')) \n" +
                "    END AS 'date', \n" +
                "    open, \n" +
                "    high, \n" +
                "    low, \n" +
                "    close, \n" +
                "    vol\n" +
                "FROM historical_raw_" + type + "\n" +
                "WHERE date >= '19700101' \n" +
                "ORDER BY date ASC;";
        return ExecuteQuery.run(url, query);
    }
}