package com.thinkbox.m2.m2_questdb.command;

import com.thinkbox.m2.m2_questdb.constants.Constants;
import com.thinkbox.m2.m2_questdb.service.ExecuteQuery;

public class SelectFromHistorialForTicker implements Constants {
    public static Object run(String url, String ticker) {
        String query = "select date, open, high, low, close, vol from historical_d where ticker = '" + ticker + "';";
        return ExecuteQuery.run(url, query);
    }
}