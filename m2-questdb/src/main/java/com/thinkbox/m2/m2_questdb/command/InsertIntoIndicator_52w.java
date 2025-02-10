package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class InsertIntoIndicator_52w implements Constants {
    public static String query = """
            WITH first_stage AS
            (SELECT\s
              'GENERAL' AS type,
              date,\s
              ticker,\s
              high,
              low,
              close,\s
              first_value(close) OVER (
                  PARTITION BY ticker       \s
                  ORDER BY date
                  ROWS 1 PRECEDING EXCLUDE CURRENT ROW
              ) AS 'previous_close',\s
              vol,
              first_value(vol) OVER (
                  PARTITION BY ticker       \s
                  ORDER BY date
                  ROWS 1 PRECEDING EXCLUDE CURRENT ROW
              ) AS 'previous_vol',
              max(high) OVER (
                PARTITION BY ticker       \s
                  ORDER BY date
                  RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW
              ) AS 'high52w',      \s
              max(high) OVER (
                PARTITION BY ticker       \s
                  ORDER BY date
                  RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
              ) AS 'previous_high52w',
              min(low) OVER (
                PARTITION BY ticker       \s
                  ORDER BY date
                  RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW
              ) AS 'low52w',      \s
              min(low) OVER (
                PARTITION BY ticker       \s
                  ORDER BY date
                  RANGE BETWEEN '365' DAY PRECEDING AND CURRENT ROW EXCLUDE CURRENT ROW
              ) AS 'previous_low52w'\s
            FROM historical_d)
            INSERT INTO indicator_52w
            SELECT
              type, date, ticker, high, low, close, previous_close, vol, previous_vol,
              high52w, previous_high52w, (close - high52w)/high52w, low52w, previous_low52w, (close - low52w)/low52w
            FROM first_stage;""";

    public static Object run(String url) {
        return ExecuteQuery.run(url, query);
    }
}