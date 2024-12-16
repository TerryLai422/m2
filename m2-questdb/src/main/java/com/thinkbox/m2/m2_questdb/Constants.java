package com.thinkbox.m2.m2_questdb;

public interface Constants {
    String importHistoricalFilePath = "/Users/admin/historical_data";
    String importHistoricalErrorPath = "/Users/admin/historical_error/";
    String importDailyFilePath = "/Users/admin/daily_data";
    String importDailyErrorPath = "/Users/admin/daily_error/";
    String importUrlTemplate = "http://%s/imp?fmt=json&forceHeader=true&name=%s";
    String execUrlTemplate = "http://%s/exec";
    String hostName = "127.0.0.1:9000";
}