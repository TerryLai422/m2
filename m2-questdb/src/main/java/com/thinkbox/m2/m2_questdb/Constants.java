package com.thinkbox.m2.m2_questdb;

public interface Constants {
    String importFilePath = "/Users/admin/historical_data";
    String importErrorPath = "/Users/admin/historical_error/";
    String importUrlTemplate = "http://%s/imp?fmt=json&forceHeader=true&name=%s";
    String execUrlTemplate = "http://%s/exec";
    String hostName = "127.0.0.1:9000";
}