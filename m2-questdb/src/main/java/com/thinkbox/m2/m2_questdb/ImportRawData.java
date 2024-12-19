package com.thinkbox.m2.m2_questdb;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportRawData implements Constants {
    public static String tableName = "historical_raw_d";

    public static void main(String[] args) {
//        run(importHistoricalFilePath, importHistoricalErrorPath, importUrlTemplate, hostName, tableName);
    }

    public static long run(String importHistoricalFilePath, String importHistoricalErrorPath,
                           String importUrlTemplate, String hostName, String tableName) {
        Path startPath = Paths.get(importHistoricalFilePath);
        String url = String.format(importUrlTemplate, hostName, tableName);
        return ImportFiles.singlethread(url, startPath, importHistoricalErrorPath);
    }
}