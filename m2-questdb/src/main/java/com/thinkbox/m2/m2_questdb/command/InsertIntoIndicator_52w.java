package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class InsertIntoIndicator_52w implements Constants {
    public static String query = "INSERT INTO indicator_52w\n" +
            "SELECT \n" +
            "    'GENERAL' AS type,\n" +
            "    date, \n" +
            "    ticker, \n" +
            "    high,\n" +
            "    low,\n" +
            "    close, \n" +
            "    first_value(close) OVER (\n" +
            "        PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        ROWS 1 PRECEDING EXCLUDE CURRENT ROW\n" +
            "    ) AS 'previous_close', \n" +
            "    vol,\n" +
            "    first_value(vol) OVER (\n" +
            "        PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        ROWS 1 PRECEDING EXCLUDE CURRENT ROW\n" +
            "    ) AS 'previous_vol',\n" +
            "    max(high) OVER (\n" +
            "      PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW\n" +
            "    ) AS '52wHigh',       \n" +
            "    max(high) OVER (\n" +
            "      PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW\n" +
            "    ) AS 'previous_52wHigh',\n" +
            "    min(low) OVER (\n" +
            "      PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW\n" +
            "    ) AS '52wLow',       \n" +
            "    min(low) OVER (\n" +
            "      PARTITION BY ticker        \n" +
            "        ORDER BY date\n" +
            "        RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW\n" +
            "    ) AS 'previous_52wLow' \n" +
            "FROM historical_d;";

    public static Object run(String url) {
        return ExecuteQuery.run(url, query);
    }
}