package com.thinkbox.m2.m2_questdb.command;

import ch.qos.logback.core.util.StringUtil;
import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class InsertIntoIndicator implements Constants {

    public static String queryTemplate1 = """
            WITH first_stage AS
            (SELECT
                date, ticker, %s AS 'value1',
                avg(%s) OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN %d PRECEDING AND CURRENT ROW
                    EXCLUDE CURRENT ROW)
                AS 'value2',
                count() OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN UNBOUNDED PRECEDING AND %d PRECEDING)
                AS 'total'
            FROM %s %s),
            """;

    public static String queryTemplate2 = """
            where date >= dateadd('d', %s, to_date('%s', 'yyyyMMdd'))
            """;

    public static String queryTemplate3 = """
            second_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                value1 - value2 AS 'difference',
                ((value1 - value2) / value2) * 100 AS 'percentage',
                first_value(value1 - value2) OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS 1 PRECEDING EXCLUDE CURRENT ROW)
                AS 'previous_difference'
            FROM first_stage
            WHERE TOTAL > 0),
            third_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                difference, previous_difference, percentage,
                CASE
                    WHEN difference >=0 and previous_difference >= 0 THEN total
                    ELSE
                        CASE
                            WHEN difference < 0 and previous_difference < 0 THEN total
                            ELSE (1 - total)
                        END
                END AS 'trend'
            FROM second_stage),
            fourth_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                difference, previous_difference, percentage, trend,
                min(trend) OVER (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
                AS 'minimum_trend'
            FROM third_stage)
            INSERT INTO %s
            SELECT
                '%s_%d' AS type, date, ticker, value1, value2, total,
                difference, previous_difference, percentage, trend, minimum_trend,
                (total + minimum_trend) AS 'trending'
            FROM fourth_stage;""";

    public static String queryTemplate = """
            WITH first_stage AS
            (SELECT
                date, ticker, %s AS 'value1',
                avg(%s) OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN %d PRECEDING AND CURRENT ROW
                    EXCLUDE CURRENT ROW)
                AS 'value2',
                count() OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN UNBOUNDED PRECEDING AND %d PRECEDING)
                AS 'total'
            FROM %s),
            second_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                value1 - value2 AS 'difference',
                ((value1 - value2) / value2) * 100 AS 'percentage',
                first_value(value1 - value2) OVER
                    (PARTITION BY ticker ORDER BY date
                    ROWS 1 PRECEDING EXCLUDE CURRENT ROW)
                AS 'previous_difference'
            FROM first_stage
            WHERE TOTAL > 0),
            third_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                difference, previous_difference, percentage,
                CASE
                    WHEN difference >=0 and previous_difference >= 0 THEN total
                    ELSE
                        CASE
                            WHEN difference < 0 and previous_difference < 0 THEN total
                            ELSE (1 - total)
                        END
                END AS 'trend'
            FROM second_stage),
            fourth_stage AS
            (SELECT
                date, ticker, value1, value2, total,
                difference, previous_difference, percentage, trend,
                min(trend) OVER (PARTITION BY ticker ORDER BY date
                    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
                AS 'minimum_trend'
            FROM third_stage)
            INSERT INTO %s
            SELECT
                '%s_%d' AS type, date, ticker, value1, value2, total,
                difference, previous_difference, percentage, trend, minimum_trend,
                (total + minimum_trend) AS 'trending'
            FROM fourth_stage;""";

    public static Object run(String url, String type, String prefix, int interval, String sourceTable, String targetName, String date) {
        String query;
        if (StringUtil.isNullOrEmpty(date)) {
            query = String.format(queryTemplate1, type, type, interval, interval, sourceTable, "");
            query += String.format(queryTemplate3, targetName, prefix, interval);
        } else {
            // AV, AV, 5, 5, historical_d, -1 * 2 * 5 (interval), 20250718,
            // indicator_AV, AV, 5
            query = String.format(queryTemplate1, type, type, interval, interval, sourceTable,
                    String.format(queryTemplate2, -1 * 2 * interval, date));
            query += String.format(queryTemplate3, targetName, prefix, interval);
        }
//        System.out.println("Query:" + query);
//        return null;
        return ExecuteQuery.run(url, query);
    }
}