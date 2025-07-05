package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

import java.util.HashMap;
import java.util.Map;

public class InsertIntoHistorical_5m implements Constants {
    public static Object run(String url, String type, String date) {
        String query;
        Long totalDuration = 0L;
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            query = "INSERT INTO historical_" + type + "_" + letter +"1\n" +
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
                    "WHERE substring(ticker, 1, 1) = '" + letter + "' and ";
            if ("".equals(date)) {
                query += "date >= '19700101' \n";
            } else {
                query += "date > '" + date + "' \n";
            }
//        query += "date = '20250306' ";
            query += "ORDER BY date, time ASC;";
            System.out.println("Query:" + query);
            Map<String, Object> map = (Map<String, Object>) ExecuteQuery.run(url, query);
            totalDuration += (Long) map.get("duration");
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("duration", totalDuration);
        System.out.println("totalDuration:" + totalDuration);
        return returnMap;
    }
}