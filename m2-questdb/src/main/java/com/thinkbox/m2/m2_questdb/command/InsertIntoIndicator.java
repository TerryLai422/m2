package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class InsertIntoIndicator implements Constants {
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

    public static Object run(String url, String type, String prefix, int interval, String sourceTable, String targetName) {
        String query = String.format(queryTemplate, type, type, interval, interval, sourceTable, targetName, prefix, interval);
        return ExecuteQuery.run(url, query);
    }
}